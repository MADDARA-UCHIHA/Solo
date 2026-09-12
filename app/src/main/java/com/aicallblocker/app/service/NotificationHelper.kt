package com.aicallblocker.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationHelper {

    private const val CHANNEL_ID = "call_summary"

    fun showCallSummary(
        context: Context,
        number: String,
        summary: String,
        isSpam: Boolean,
        details: String? = null
    ) {
        if (android.os.Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        val manager = context.getSystemService(NotificationManager::class.java)
        if (manager.getNotificationChannel(CHANNEL_ID) == null) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_ID, "Qo'ng'iroq xulosalari", NotificationManager.IMPORTANCE_DEFAULT)
            )
        }

        val title = if (isSpam) "⚠️ Ehtimol spam qo'ng'iroq: $number" else "Qo'ng'iroq xulosasi: $number"

        val body = listOfNotNull(summary, details?.takeIf { it.isNotBlank() }).joinToString("\n")
        val blockIntent = actionIntent(context, NotificationActionReceiver.ACTION_BLOCK, number)
        val trustIntent = actionIntent(context, NotificationActionReceiver.ACTION_TRUST, number)
        val detailsIntent = actionIntent(context, NotificationActionReceiver.ACTION_DETAILS, number, body)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .addAction(android.R.drawable.ic_delete, "Raqamni bloklash", blockIntent)
            .addAction(android.R.drawable.ic_menu_save, "Ishonchli", trustIntent)
            .addAction(android.R.drawable.ic_menu_info_details, "Transkripsiya", detailsIntent)
            .build()

        NotificationManagerCompat.from(context).notify(number.hashCode(), notification)
    }

    private fun actionIntent(
        context: Context,
        action: String,
        number: String,
        details: String? = null
    ): PendingIntent {
        val intent = Intent(context, NotificationActionReceiver::class.java).apply {
            this.action = action
            putExtra(NotificationActionReceiver.EXTRA_NUMBER, number)
            putExtra(NotificationActionReceiver.EXTRA_DETAILS, details)
        }
        return PendingIntent.getBroadcast(
            context,
            "$action:$number".hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
