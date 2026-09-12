package com.aicallblocker.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NumberEntity::class, CallRecordEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun numberDao(): NumberDao
    abstract fun callRecordDao(): CallRecordDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ai_call_blocker.db"
                ).addMigrations(MIGRATION_1_2).build().also { INSTANCE = it }
            }

        private val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS call_records (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "phoneNumber TEXT NOT NULL, reason TEXT NOT NULL, " +
                        "spamScore REAL NOT NULL, blockedAt INTEGER NOT NULL)"
                )
            }
        }
    }
}
