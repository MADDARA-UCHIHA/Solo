package com.aicallblocker.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ListType { BLACKLIST, WHITELIST }

@Entity(tableName = "numbers")
data class NumberEntity(
    @PrimaryKey val phoneNumber: String,
    val listType: ListType,
    val label: String? = null,
    val spamScore: Float = 0f,
    val addedAt: Long = System.currentTimeMillis()
)
