package com.aicallblocker.app.service

import android.util.Log
data class TranscriptAnalysis(
    val summary: String,
    val isSpamLikely: Boolean,
    val spamScore: Float,
    val toneRiskScore: Float = 0f,
    val reasons: List<String> = emptyList()
)

/**
 * Qurilmada ishlaydigan, tarmoq talab qilmaydigan bazaviy analizator.
 * Bu model LLM o'rnini bosmaydi, lekin maxfiylikni saqlagan holda tezkor
 * signal beradi va keyinchalik MediaPipe/Gemini Nano adapteri bilan almashtirilishi mumkin.
 */
object CallTranscriptAnalyzer {

    private val fraudPatterns = listOf(
        "karta raqami" to "Karta raqami so'ralgan",
        "sms kod" to "SMS kod so'ralgan",
        "tasdiqlash kodi" to "Tasdiqlash kodi so'ralgan",
        "cvv" to "CVV so'ralgan",
        "parolni ayting" to "Parol so'ralgan",
        "kodni yuboring" to "Kod yuborish so'ralgan",
        "pasport ma'lumot" to "Pasport ma'lumoti so'ralgan"
    )
    private val contextPatterns = listOf(
        Regex("\\b(bank|xavfsizlik|operator)\\b.*(kod|parol|karta)") to "Tashkilot nomidan maxfiy ma'lumot so'ralgan",
        Regex("(sovrin|yutuq|mukofot).*(to'lov|komissiya|karta)") to "Yutuq uchun to'lov talab qilingan",
        Regex("(investitsiya|daromad).*(kafolat|oldindan|pul)") to "Shubhali investitsiya va'dasi"
    )
    private val threatKeywords = listOf(
        "hozir yuboring", "aks holda", "hibsga olin", "bloklanadi",
        "sir saqlang", "zudlik bilan", "qo'rqitm"
    )
    private val sensitiveRequestKeywords = listOf(
        "sms kod", "cvv", "karta raqami", "pasport", "parol"
    )

    fun analyze(transcript: String): TranscriptAnalysis {
        val lower = transcript.lowercase()
        val fraudHits = fraudPatterns.filter { lower.contains(it.first) }
        val contextHits = contextPatterns.filter { it.first.containsMatchIn(lower) }
        val threatHits = threatKeywords.count { lower.contains(it) }
        val sensitiveHits = sensitiveRequestKeywords.count { lower.contains(it) }
        val toneRisk = (
            threatHits * 0.22f +
                sensitiveHits * 0.12f +
                Regex("[!?]{2,}|\\bTEZ\\b|\\bHOZIR\\b").findAll(transcript).count() * 0.08f
            ).coerceIn(0f, 1f)
        val score = (
            fraudHits.size * 0.18f +
                contextHits.size * 0.2f +
                toneRisk * 0.3f
            ).coerceIn(0f, 1f)
        val reasons = buildList {
            fraudHits.forEach { add(it.second) }
            contextHits.forEach { add(it.second) }
            if (threatHits > 0) add("Bosim yoki tahdid ohangi")
            if (sensitiveHits > 0) add("Maxfiy ma'lumot so'ralgan")
        }

        val summary = if (transcript.length > 140) {
            transcript.take(140).trimEnd() + "..."
        } else transcript

        return TranscriptAnalysis(
            summary = summary,
            isSpamLikely = score >= 0.25f || toneRisk >= 0.45f,
            spamScore = score,
            toneRiskScore = toneRisk,
            reasons = reasons
        )
    }

    fun analyzeSafely(transcript: String): TranscriptAnalysis? {
        return try {
            analyze(transcript)
        } catch (exception: RuntimeException) {
            Log.e(TAG, "Transcript tahlili bajarilmadi", exception)
            null
        }
    }

    private const val TAG = "CallTranscriptAnalyzer"
}
