package com.aicallblocker.app.service

import com.aicallblocker.app.data.remote.CallSessionSummary
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

/**
 * Backend (masalan Twilio webhook orqali AI suhbatni yakunlagach) shu
 * ilovaga FCM push yuboradi. Bu — audio emas, faqat matn/JSON payload:
 * chaqiruvchi raqam, transkript, AI xulosasi, spam skori.
 *
 * Ilova hech qachon xom audio bilan ishlamaydi — bu arxitekturaning
 * asosiy afzalligi: Android tomonida hech qanday maxfiylik/OEM cheklovi
 * yo'q, chunki og'ir audio ishi to'liq serverda (WebRTC/SIP + AI) bo'ladi.
 */
class VoipPushService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // TODO: yangi tokenni backend'ga yuborish (VoipBackendApi.registerForwarding)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val payload = message.data["session"] ?: return

        val session = try {
            Gson().fromJson(payload, CallSessionSummary::class.java)
        } catch (e: Exception) {
            return
        }

        NotificationHelper.showCallSummary(
            context = applicationContext,
            number = session.callerNumber,
            summary = session.aiSummary,
            isSpam = session.isSpamLikely,
            details = "Spam skori: ${session.spamScore}\n${session.transcript.take(500)}"
        )
    }
}
