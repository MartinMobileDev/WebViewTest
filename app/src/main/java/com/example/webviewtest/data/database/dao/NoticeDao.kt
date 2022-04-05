package com.example.webviewtest.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.webviewtest.data.database.entities.NoticeEntity

@Dao
interface NoticeDao {
    @Query("SELECT * FROM notice_table")
    suspend fun getAllNotice(): List<NoticeEntity>

    @Insert
    suspend fun insertAll(notices: List<NoticeEntity>)

    @Query("DELETE FROM notice_table")
    suspend fun deleteAllNotices()

    @Query("UPDATE notice_table SET active=0 WHERE id = :id")
    suspend fun updateNotice(id: Int) {
    }
}