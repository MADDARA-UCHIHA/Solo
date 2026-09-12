package com.aicallblocker.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_records")
data class CallRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val reason: String,
    val spamScore: Float,
    val blockedAt: Long = System.currentTimeMillis()
)
