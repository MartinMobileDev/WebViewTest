package com.example.webviewtest.domain

import com.example.webviewtest.data.NoticeRepository
import com.example.webviewtest.data.database.entities.toDatabase
import com.example.webviewtest.domain.model.Notice
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NoticeRepository) {

    suspend operator fun invoke(): List<Notice> {
        val notices = repository.getAllNewsFromApi()
        return if (notices.isNotEmpty()) {
            repository.clearNotices()
            repository.insertNotices(notices.map { it.toDatabase() })
            notices
        } else {
            repository.getAllNewsFromDatabase()
        }
    }
}