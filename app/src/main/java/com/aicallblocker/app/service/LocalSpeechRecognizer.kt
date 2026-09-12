package com.aicallblocker.app.service

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.Locale

/**
 * Foydalanuvchi boshlab bergan audio uchun on-device STT adapteri.
 * Android qurilmasida offline til paketi bo'lsa, tarmoqsiz ham ishlaydi.
 * PSTN qo'ng'iroq audiosini o'zboshimchalik bilan yozib olmaydi.
 */
class LocalSpeechRecognizer(
    context: Context,
    private val onPartial: (String) -> Unit,
    private val onFinal: (String) -> Unit,
    private val errorCallback: (Int) -> Unit,
    private val stateCallback: (String) -> Unit = {}
) : RecognitionListener {
    private val recognizer: SpeechRecognizer? =
        if (context.checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) ==
            PackageManager.PERMISSION_GRANTED && SpeechRecognizer.isRecognitionAvailable(context)
        ) {
            runCatching { SpeechRecognizer.createSpeechRecognizer(context.applicationContext) }
                .onFailure { Log.e(TAG, "SpeechRecognizer yaratilmadi", it) }
                .getOrNull()
        } else {
            null
        }

    init {
        recognizer?.setRecognitionListener(this)
            ?: emitState("Mikrofon ruxsati yoki STT xizmati mavjud emas")
    }

    fun start() {
        val activeRecognizer = recognizer ?: run {
            emitState("STT ishga tushmadi: mikrofon ruxsatini tekshiring")
            return
        }
        try {
            activeRecognizer.startListening(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            })
            emitState("Tinglanmoqda...")
        } catch (exception: RuntimeException) {
            Log.e(TAG, "STT startListening xatosi", exception)
            emitState("STT xatosi: mikrofonni ishga tushirib bo'lmadi")
        }
    }

    fun stop() {
        runCatching { recognizer?.stopListening() }
            .onFailure { Log.w(TAG, "STT to'xtatilmadi", it) }
    }

    fun destroy() {
        runCatching { recognizer?.destroy() }
            .onFailure { Log.w(TAG, "STT resurslari yopilmadi", it) }
    }

    override fun onPartialResults(results: Bundle?) {
        runCatching {
            results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.firstOrNull()?.let(onPartial)
        }.onFailure { Log.w(TAG, "Partial STT natijasi qayta ishlanmadi", it) }
    }

    override fun onResults(results: Bundle?) {
        runCatching {
            results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.firstOrNull()?.let(onFinal)
            emitState("Tayyor")
        }.onFailure { Log.w(TAG, "Final STT natijasi qayta ishlanmadi", it) }
    }

    override fun onError(error: Int) {
        Log.w(TAG, "SpeechRecognizer xatosi: $error")
        runCatching { errorCallback(error) }
            .onFailure { Log.w(TAG, "STT xatosi UI'ga yetkazilmadi", it) }
        emitState("STT xatosi: $error")
    }
    override fun onReadyForSpeech(params: Bundle?) = Unit
    override fun onBeginningOfSpeech() = Unit
    override fun onRmsChanged(rmsdB: Float) = Unit
    override fun onBufferReceived(buffer: ByteArray?) = Unit
    override fun onEndOfSpeech() = Unit
    override fun onEvent(eventType: Int, params: Bundle?) = Unit

    private fun emitState(message: String) {
        runCatching { stateCallback(message) }
            .onFailure { Log.w(TAG, "STT holati UI'ga yetkazilmadi", it) }
    }

    companion object {
        private const val TAG = "LocalSpeechRecognizer"
    }
}
