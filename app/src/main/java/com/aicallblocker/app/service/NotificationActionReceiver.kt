package com.aicallblocker.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val number = intent.getStringExtra(EXTRA_NUMBER) ?: return
        when (intent.action) {
            ACTION_DETAILS -> {
                context.startActivity(Intent(context, com.aicallblocker.app.ui.MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra(com.aicallblocker.app.ui.MainActivity.EXTRA_NUMBER_TO_SHOW, number)
                    putExtra(
                        com.aicallblocker.app.ui.MainActivity.EXTRA_DETAILS_TO_SHOW,
                        intent.getStringExtra(EXTRA_DETAILS)
                    )
                })
            }
            ACTION_BLOCK, ACTION_TRUST -> {
                val pendingResult = goAsync()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = com.aicallblocker.app.data.repository.CallRepository.getInstance(context)
                        if (intent.action == ACTION_BLOCK) {
                            repository.addToBlacklist(number, "Notification orqali foydalanuvchi xabari")
                        } else {
                            repository.addToWhitelist(number, "Notification orqali ishonchli")
                        }
                    } finally {
                        pendingResult.finish()
                    }
                }
            }
        }
    }

    companion object {
        const val ACTION_BLOCK = "com.aicallblocker.app.action.BLOCK"
        const val ACTION_TRUST = "com.aicallblocker.app.action.TRUST"
        const val ACTION_DETAILS = "com.aicallblocker.app.action.DETAILS"
        const val EXTRA_NUMBER = "extra_notification_number"
        const val EXTRA_DETAILS = "extra_notification_details"
    }
}
