package com.aicallblocker.app.data.repository

import android.content.Context
import com.aicallblocker.app.data.local.AppDatabase
import com.aicallblocker.app.data.local.ListType
import com.aicallblocker.app.data.local.NumberEntity
import com.aicallblocker.app.data.local.CallRecordEntity
import kotlinx.coroutines.flow.Flow
import com.aicallblocker.app.data.remote.RetrofitClient
import com.aicallblocker.app.domain.model.CallVerdict
import retrofit2.HttpException
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class CallRepository(private val context: Context) {

    private val dao = AppDatabase.getInstance(context).numberDao()
    private val records = AppDatabase.getInstance(context).callRecordDao()

    /**
     * FAQAT mahalliy Room bazasidan tekshiradi. Tarmoqqa chiqmaydi.
     * CallScreeningService.onScreenCall() ichida timeout bilan chaqiriladi —
     * chunki tizim bu metodga juda qisqa vaqt beradi va tarmoq so'rovi
     * deadline'ni oshirib yuborishi mumkin.
     */
    suspend fun evaluateLocalOnly(rawNumber: String): CallVerdict {
        val normalized = normalize(rawNumber)
        val local = dao.findByNumber(normalized)
        return when (local?.listType) {
            ListType.BLACKLIST -> CallVerdict.Blacklisted(local.label ?: "Local blacklist")
            ListType.WHITELIST -> CallVerdict.Whitelisted
            null -> CallVerdict.Unknown(0f)
        }
    }

    /**
     * Cloud API orqali global spam bazasini tekshiradi (tarmoqqa chiqadi,
     * sekin bo'lishi mumkin). Screening deadline'idan TASHQARIDA, fonda
     * chaqirilishi kerak — hech qachon onScreenCall() ichida to'g'ridan-to'g'ri emas.
     */
    suspend fun evaluateRemote(rawNumber: String): CallVerdict {
        val normalized = normalize(rawNumber)
        return try {
            val remote = RetrofitClient.api.checkNumber(normalized)
            if (remote.isSpam) {
                // keyingi safar tezroq bo'lishi uchun mahalliy keshga yozib qo'yamiz
                dao.upsert(
                    NumberEntity(
                        phoneNumber = normalized,
                        listType = ListType.BLACKLIST,
                        label = "Cloud spam DB",
                        spamScore = remote.spamScore
                    )
                )
                CallVerdict.Blacklisted("Cloud spam DB (score=${remote.spamScore})")
            } else {
                CallVerdict.Unknown(remote.spamScore)
            }
        } catch (_: java.io.IOException) {
            // tarmoq yo'q bo'lsa — noaniq deb hisoblab, bloklamaymiz
            CallVerdict.Unknown(0f)
        } catch (_: HttpException) {
            CallVerdict.Unknown(0f)
        }
    }

    suspend fun addToBlacklist(number: String, label: String? = null) {
        val normalized = normalize(number)
        dao.upsert(NumberEntity(normalized, ListType.BLACKLIST, label))
        try {
            RetrofitClient.api.reportNumber(com.aicallblocker.app.data.remote.SpamReportRequest(normalized))
        } catch (_: java.io.IOException) {
            // Local report remains authoritative and will be retried by a future sync.
        } catch (_: HttpException) {
            // Local report remains authoritative and will be retried by a future sync.
        }
    }

    suspend fun addToWhitelist(number: String, label: String? = null) {
        dao.upsert(NumberEntity(normalize(number), ListType.WHITELIST, label))
    }

    fun observeBlockedCalls(): Flow<List<CallRecordEntity>> = records.observeRecent()

    suspend fun recordBlockedCall(number: String, reason: String, spamScore: Float) {
        records.insert(CallRecordEntity(phoneNumber = normalize(number), reason = reason, spamScore = spamScore))
    }

    suspend fun getReasonStats() = records.countByReason()

    suspend fun exportBackup(): String {
        return Gson().toJson(BackupPayload(dao.getAll(), records.getAll()))
    }

    suspend fun exportCsv(): String {
        val rows = buildString {
            appendLine("type,phoneNumber,label,reason,spamScore,timestamp")
            dao.getAll().forEach {
                appendLine("number,${it.phoneNumber},${csv(it.label)},,,${it.addedAt}")
            }
            records.getAll().forEach {
                appendLine("call,${it.phoneNumber},,${csv(it.reason)},${it.spamScore},${it.blockedAt}")
            }
        }
        return rows
    }

    suspend fun importBackup(json: String) {
        val backup = Gson().fromJson(json, BackupPayload::class.java)
        backup.numbers.orEmpty().forEach { dao.upsert(it) }
        backup.calls.orEmpty().forEach { records.insert(it.copy(id = 0)) }
    }

    private fun csv(value: String?): String =
        "\"${(value ?: "").replace("\"", "\"\"")}\""

    private fun normalize(number: String): String =
        number.filter { it.isDigit() || it == '+' }

    companion object {
        @Volatile private var INSTANCE: CallRepository? = null
        fun getInstance(context: Context): CallRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CallRepository(context.applicationContext).also { INSTANCE = it }
            }

    }
}

data class BackupPayload(
    @SerializedName("numbers") val numbers: List<NumberEntity>? = emptyList(),
    @SerializedName("calls") val calls: List<CallRecordEntity>? = emptyList()
)
