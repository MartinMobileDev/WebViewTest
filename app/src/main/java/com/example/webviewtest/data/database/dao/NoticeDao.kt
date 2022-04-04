package com.example.webviewtest.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.webviewtest.data.database.entities.NoticeEntity
import retrofit2.http.DELETE

@Dao
interface NoticeDao {
    @Query("SELECT * FROM notice_table ORDER BY createdAt DESC")
    suspend fun getAllNotice():List<NoticeEntity>

    @Insert
    suspend fun insertAll(notices:List<NoticeEntity>)

    @Query("DELETE FROM notice_table")
    suspend fun deleteAllNotices()
}