package com.aicallblocker.app.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Bu — Android ilova va SIZ QURADIGAN backend o'rtasidagi SHARTNOMA
 * (contract). Backendning o'zi (Twilio/Vonage webhook handler, STT/LLM/TTS
 * orkestratsiyasi) bu loyiha doirasidan tashqarida — u alohida server
 * proyekti (masalan Node.js/Python) bo'lishi kerak, chunki WebRTC/SIP
 * signalizatsiya va AI orkestratsiyasi odatda serverda joylashadi.
 */

data class RegisterForwardingRequest(
    val userId: String,
    val virtualNumber: String,
    val fcmToken: String
)

data class CallSessionSummary(
    val sessionId: String,
    val callerNumber: String,
    val transcript: String,
    val aiSummary: String,
    val isSpamLikely: Boolean,
    val spamScore: Float,
    val timestamp: Long
)

interface VoipBackendApi {

    /** Foydalanuvchini backend'ga ro'yxatdan o'tkazish: qaysi virtual raqam
     * unga tegishli va push xabarlarni qayerga yuborish kerak. */
    @POST("v1/forwarding/register")
    suspend fun registerForwarding(@Body request: RegisterForwardingRequest)

    /** O'tgan AI-boshqargan qo'ng'iroqlar tarixini olish (agar push o'tkazib
     * yuborilgan bo'lsa yoki UI ro'yxat ko'rsatishi kerak bo'lsa). */
    @GET("v1/sessions/{userId}")
    suspend fun getCallSessions(@Path("userId") userId: String): List<CallSessionSummary>
}
