package com.aicallblocker.app.service

import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log
import com.aicallblocker.app.data.repository.CallRepository
import com.aicallblocker.app.domain.model.CallVerdict
import kotlinx.coroutines.*

/**
 * MUHIM ARXITEKTURA TUSHUNTIRISHI (forward qilish mexanizmi):
 *
 * Operator darajasidagi CFNRy (Call Forwarding No Reply) — bu STATIK,
 * UMUMIY qoida: "agar timeout ichida javob berilmasa, HAR QANDAY
 * qo'ng'iroqni virtual raqamga yubor". Operatorga "faqat shu bitta raqamni
 * forward qil" deb ayta olmaymiz — bu funksiya mavjud emas.
 *
 * Shu sababli "faqat spamni forward qilish" effekti quyidagicha erishiladi:
 *   - Xavfsiz/tanish raqam → oddiy jiringlaydi → foydalanuvchi javob
 *     beradi → CFNRy hech qachon ishga tushmaydi.
 *   - Shubhali/spam raqam → `setSilenceCall(true)` bilan JIM qilinadi
 *     (jiringlamaydi) → "javobsiz qoladi" → CFNRy timeout'i o'tgach,
 *     operator AVTOMATIK uni virtual raqamga yo'naltiradi → AI javob beradi.
 *
 * Ya'ni: forwarding qoidasi bitta va umumiy, lekin QAYSI qo'ng'iroq
 * "javobsiz qoladi" — buni shu servis real vaqtda hal qiladi.
 *
 * CHEKLOV: bu CFNRy timeout'i (odatda 5-30s, operatorga bog'liq) qadar
 * kechikish demakdir — AI darhol emas, biroz kutib javob beradi.
 * Shuningdek, `Unknown` (hali noaniq) raqamlarni jim qilish — agar u aslida
 * tanish odam bo'lsa — soxta-musbat (false positive) natija berishi mumkin.
 */
class MyCallScreeningService : CallScreeningService() {

    private val repository by lazy { CallRepository.getInstance(applicationContext) }
    // SupervisorJob: bitta so'rov muvaffaqiyatsiz bo'lsa ham servis butunligi buzilmasin
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onScreenCall(callDetails: Call.Details) {
        val phoneNumber = callDetails.handle?.schemeSpecificPart ?: ""
        CallDashboardStateStore.update { it.copy(activeNumber = phoneNumber, status = "Qo'ng'iroq tekshirilmoqda") }

        scope.launch {
            val responseBuilder = CallResponse.Builder()

            // Faqat mahalliy Room bazasini so'raymiz, va uni ham timeout bilan cheklaymiz.
            val localVerdict = runCatching {
                withTimeoutOrNull(LOCAL_LOOKUP_TIMEOUT_MS) {
                    repository.evaluateLocalOnly(phoneNumber)
                }
            }.onFailure {
                Log.e(TAG, "Mahalliy qo'ng'iroq tekshiruvi bajarilmadi", it)
            }.getOrNull()

            when (localVerdict) {
                is CallVerdict.Blacklisted -> {
                    runCatching {
                        repository.recordBlockedCall(phoneNumber, localVerdict.reason, 1f)
                    }.onFailure {
                        Log.e(TAG, "Bloklangan qo'ng'iroq tarixi saqlanmadi", it)
                    }
                    responseBuilder
                        .setDisallowCall(true)
                        .setRejectCall(true)
                        .setSkipCallLog(false)
                        .setSkipNotification(false)
                }
                is CallVerdict.Whitelisted -> {
                    responseBuilder.setDisallowCall(false)
                }
                is CallVerdict.Unknown, null -> {
                    // Aniq qora ro'yxatda emas, lekin "xavfsiz" deb ham
                    // kafolatlanmagan. Qo'ng'iroqni RAD ETMAYMIZ (fail-open),
                    // lekin JIM qilamiz — shu orqali agar tizim javob
                    // bermasa, operatorning CFNRy qoidasi uni virtual
                    // raqamga (AI'ga) yo'naltiradi (yuqoridagi izohga qarang).
                    // Foydalanuvchi ekranda qo'ng'iroqni ko'radi (rad
                    // etilmagan), faqat ovozli/vibro signal bo'lmaydi.
                    responseBuilder
                        .setDisallowCall(false)
                        .setSilenceCall(true)
                        .setSkipNotification(false)
                    // Cloud tekshiruvini fonda, deadline'dan tashqarida
                    // davom ettiramiz — keyingi safar uchun keshlash va
                    // statistikaga foydali.
                    scheduleBackgroundDeepCheck(phoneNumber)
                }
            }

            // Deadline'dan oldin har doim javob qaytarilishi SHART.
            respondToCall(callDetails, responseBuilder.build())
            CallDashboardStateStore.update { it.copy(status = "Qo'ng'iroq qarori: ${localVerdict?.javaClass?.simpleName}") }
        }
    }

    /**
     * Cloud spam-bazasini tekshirish — screening deadline'idan keyin,
     * mustaqil ravishda bajariladi. Bu qo'ng'iroq allaqachon `setSilenceCall`
     * bilan jim qilingan va CFNRy orqali AI'ga (agar javobsiz qolsa)
     * AVTOMATIK boradi — ilova bu yerda AI handlerni alohida "ishga
     * tushirishi" shart emas. Bu funksiyaning vazifasi faqat: keyingi safar
     * tezroq bo'lishi uchun natijani mahalliy keshga yozib qo'yish.
     */
    private fun scheduleBackgroundDeepCheck(number: String) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            runCatching {
                when (val verdict = repository.evaluateRemote(number)) {
                    is CallVerdict.Blacklisted ->
                        repository.recordBlockedCall(number, verdict.reason, 1f)
                    else -> Unit
                }
            }.onFailure {
                Log.w(TAG, "Fon spam tekshiruvi bajarilmadi", it)
            }
            // Natija repository ichida avtomatik keshlanadi (agar spam bo'lsa).
        }
    }

    companion object {
        private const val TAG = "MyCallScreeningService"
        // Xavfsiz chegara: tizim odatda ~1-2 soniya beradi, lekin biz
        // buferga ancha katta joy qoldirib, faqat mahalliy DB uchun 150ms beramiz.
        private const val LOCAL_LOOKUP_TIMEOUT_MS = 150L
        const val EXTRA_NUMBER = "extra_number"
    }
}
