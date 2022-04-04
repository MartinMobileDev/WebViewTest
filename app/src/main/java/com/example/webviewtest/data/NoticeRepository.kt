package com.example.webviewtest.data

import com.example.webviewtest.data.database.dao.NoticeDao
import com.example.webviewtest.data.database.entities.NoticeEntity
import com.example.webviewtest.data.network.NoticeService
import com.example.webviewtest.domain.model.Notice
import com.example.webviewtest.domain.model.toDomain
import javax.inject.Inject

class NoticeRepository @Inject constructor(
    private val api: NoticeService,
    private val noticeDao: NoticeDao
) {

    suspend fun getAllNewsFromApi(): List<Notice> {
        return api.getNews().map { it.toDomain() }
    }

    suspend fun getAllNewsFromDatabase(): List<Notice> {
        return noticeDao.getAllNotice().map { it.toDomain() }
    }

    suspend fun insertNotices(notices: List<NoticeEntity>) {
        noticeDao.insertAll(notices)
    }

    suspend fun clearNotices() {
        noticeDao.deleteAllNotices()
    }
}