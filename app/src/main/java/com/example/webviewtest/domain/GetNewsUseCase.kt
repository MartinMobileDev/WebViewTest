package com.example.webviewtest.domain

import com.example.webviewtest.data.NoticeRepository
import com.example.webviewtest.data.model.Notice
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NoticeRepository) {

    suspend operator fun invoke(): List<Notice>? = repository.getAllNews()
}