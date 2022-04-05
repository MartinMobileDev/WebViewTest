package com.example.webviewtest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.webviewtest.data.database.dao.NoticeDao
import com.example.webviewtest.data.database.entities.NoticeEntity

@Database(entities = [NoticeEntity::class], version = 3)
abstract class NoticeDatabase : RoomDatabase() {

    abstract fun getNoticeDao(): NoticeDao

    companion object {
        var INSTANCE: NoticeDatabase? = null

        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notice_table ADD COLUMN active INTEGER DEFAULT 1")
            }
        }
        private val migration_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notice_table ADD COLUMN parent_id INTEGER DEFAULT 1")
            }
        }

        fun createDatabase(context: Context): NoticeDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, NoticeDatabase::class.java, "asd"
                )
                    .addMigrations(migration_2_3).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}