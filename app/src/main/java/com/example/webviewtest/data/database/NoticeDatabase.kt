package com.example.webviewtest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.webviewtest.data.database.dao.NoticeDao
import com.example.webviewtest.data.database.entities.NoticeEntity

@Database(entities = [NoticeEntity::class], version = 1)
abstract class NoticeDatabase : RoomDatabase() {

    abstract fun getNoticeDao():NoticeDao
}