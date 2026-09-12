package com.aicallblocker.app.service

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.VibrationEffect
import android.os.Vibrator

object CallAlertHelper {
    fun alertHighRisk(context: Context) {
        val vibrator = context.getSystemService(Vibrator::class.java)
        if (vibrator?.hasVibrator() == true) {
            val effect = VibrationEffect.createOneShot(450, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        }
        val tone = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 85)
        tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 350)
        tone.release()
    }
}
