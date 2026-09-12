package com.aicallblocker.app.service

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.toByteString

/**
 * ESLATMA — ARXITEKTURA O'ZGARDI (YO'L B tanlandi):
 * Ushbu klass ilgari "qurilmadan backend'ga audio streaming" g'oyasi uchun
 * yozilgan edi. Tanlangan yakuniy arxitekturada (VoIP/SIP virtual raqam +
 * shartli call forwarding — qarang: `ConditionalCallForwardingManager`,
 * `VoipPushService`) qo'ng'iroq audiosi QURILMAGA UMUMAN TUSHMAYDI — u
 * to'g'ridan-to'g'ri operator tarmog'idan virtual raqamga (Twilio/Vonage)
 * boradi va AI bilan suhbat serverda tugaydi. Android ilova faqat yakuniy
 * matn/xulosani (`VoipPushService` orqali) oladi.
 *
 * Shu sababli bu klass endi ASOSIY oqimda ISHLATILMAYDI. Uni faqat quyidagi
 * holatlarda saqlab qolish mantiqiy:
 *  - Agar kelajakda ilova ichida qo'shimcha, foydalanuvchi ONG'IDA ishga
 *    tushiradigan "jonli AI yordamchi bilan chat/voice" funksiyasi
 *    qo'shilsa (masalan foydalanuvchi ilovada tugmani bosib, AI bilan
 *    to'g'ridan-to'g'ri gaplashadi) — bu holda mikrofon ruxsati orqali
 *    ODDIY (qo'ng'iroqqa aloqasi yo'q) audio yozib olish qonuniy va oddiy.
 *
 * Pastdagi kod o'zgarishsiz — faqat kelajakdagi shunday foydalanish holati
 * uchun namuna sifatida qoldirilgan.
 */
class AiCallHandlerService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var webSocket: WebSocket? = null
    private val transcriptBuffer = StringBuilder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val number = intent?.getStringExtra(MyCallScreeningService.EXTRA_NUMBER) ?: "unknown"
        try {
            startForeground(NOTIF_ID, buildForegroundNotification())
            connectVoiceStream(number)
        } catch (exception: SecurityException) {
            Log.e(TAG, "Foreground service ishga tushmadi: ruxsat yoki service type tekshiring", exception)
            stopSelf(startId)
        } catch (exception: RuntimeException) {
            Log.e(TAG, "AI call service ishga tushmadi", exception)
            stopSelf(startId)
        }
        return START_NOT_STICKY
    }

    private fun connectVoiceStream(number: String) {
        val client = OkHttpClient.Builder()
            .pingInterval(15, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url("wss://api.yourvoicebackend.example/v1/voice-stream?caller=$number")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(ws: WebSocket, response: Response) {
                // Boshlang'ich AI salomlashuv buyrug'ini yuboramiz
                ws.send(
                    """{"event":"start","greeting":"Kim bilan gaplashmoqchisiz va qaysi masalada?"}"""
                )
            }

            override fun onMessage(ws: WebSocket, text: String) {
                // Backend STT natijasi yoki metadata JSON yuborishi mumkin
                handleServerEvent(text)
            }

            override fun onMessage(ws: WebSocket, bytes: ByteString) {
                // Backenddan kelgan Neural TTS audio chunk (masalan PCM/Opus)
                playAudioChunk(bytes)
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                stopSelf()
            }

            override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                scope.launch { finalizeCallAnalysis(number) }
                stopSelf()
            }
        })
    }

    /**
     * PCM audio chunk'ni backendga jo'natish.
     * CHAQIRUVCHI TOMONDAN TA'MINLANISHI KERAK: bu funksiya faqat transport
     * qatlami. Audio manbai — YO'L A tanlansa `MyInCallService`/`Connection`
     * orqali olingan qonuniy audio buffer, YO'L B tanlansa ilovaning o'z
     * VoIP audio pipeline'i bo'lishi kerak. Bu klass o'zi audio "yozib olmaydi".
     */
    fun sendMicChunk(pcmChunk: ByteArray) {
        val consent = getSharedPreferences(PRIVACY_PREFS, MODE_PRIVATE)
            .getBoolean(CLOUD_VOICE_CONSENT, false)
        if (consent) webSocket?.send(pcmChunk.toByteString())
    }

    fun setCloudVoiceConsent(enabled: Boolean) {
        getSharedPreferences(PRIVACY_PREFS, MODE_PRIVATE)
            .edit().putBoolean(CLOUD_VOICE_CONSENT, enabled).apply()
    }

    private fun handleServerEvent(json: String) {
        // Kutilgan format: {"event":"partial_transcript","text":"..."}
        // yoki {"event":"final_transcript","text":"..."}
        if (json.contains("transcript")) {
            val text = Regex("\"text\":\"(.*?)\"").find(json)?.groupValues?.get(1)
            text?.let { transcriptBuffer.append(it).append(" ") }
        }
    }

    private fun playAudioChunk(bytes: ByteString) {
        // TODO: AudioTrack orqali chiquvchi qo'ng'iroq audio kanaliga yozish
    }

    /** Suhbat tugagach: matnni tahlil qilib, xulosa va notification yuborish. */
    private suspend fun finalizeCallAnalysis(number: String) {
        val fullText = transcriptBuffer.toString().trim()
        if (fullText.isEmpty()) return

        val analysis = CallTranscriptAnalyzer.analyze(fullText)
        NotificationHelper.showCallSummary(
            context = applicationContext,
            number = number,
            summary = analysis.summary,
            isSpam = analysis.isSpamLikely,
            details = analysis.reasons.joinToString("; ")
        )
    }

    private fun buildForegroundNotification(): Notification {
        val channelId = "ai_call_handler"
        val manager = getSystemService(NotificationManager::class.java)
        if (manager.getNotificationChannel(channelId) == null) {
            manager.createNotificationChannel(
                NotificationChannel(channelId, "AI Call Handling", NotificationManager.IMPORTANCE_LOW)
            )
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("AI Assistant qo'ng'iroqqa javob bermoqda")
            .setSmallIcon(android.R.drawable.sym_call_incoming)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        webSocket?.close(1000, "done")
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        private const val TAG = "AiCallHandlerService"
        private const val NOTIF_ID = 42
        private const val PRIVACY_PREFS = "privacy"
        private const val CLOUD_VOICE_CONSENT = "cloud_voice_consent"
    }
}
