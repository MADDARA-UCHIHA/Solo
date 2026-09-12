package com.aicallblocker.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NumberDao {

    @Query("SELECT * FROM numbers WHERE phoneNumber = :number LIMIT 1")
    suspend fun findByNumber(number: String): NumberEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: NumberEntity)

    @Query("SELECT * FROM numbers WHERE listType = :type")
    suspend fun getAllByType(type: ListType): List<NumberEntity>

    @Query("SELECT * FROM numbers ORDER BY addedAt ASC")
    suspend fun getAll(): List<NumberEntity>

    @Query("DELETE FROM numbers WHERE phoneNumber = :number")
    suspend fun delete(number: String)
}
