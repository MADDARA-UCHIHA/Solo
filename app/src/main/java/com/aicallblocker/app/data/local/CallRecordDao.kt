package com.aicallblocker.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CallRecordDao {
    @Insert
    suspend fun insert(record: CallRecordEntity)

    @Query("SELECT * FROM call_records ORDER BY blockedAt DESC LIMIT :limit")
    fun observeRecent(limit: Int = 50): Flow<List<CallRecordEntity>>

    @Query("SELECT * FROM call_records ORDER BY blockedAt ASC")
    suspend fun getAll(): List<CallRecordEntity>

    @Query("SELECT COUNT(*) FROM call_records")
    suspend fun count(): Int

    @Query("SELECT reason, COUNT(*) AS total FROM call_records GROUP BY reason ORDER BY total DESC")
    suspend fun countByReason(): List<ReasonCount>
}

data class ReasonCount(val reason: String, val total: Long)
