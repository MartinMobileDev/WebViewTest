package com.example.webviewtest.data

import com.example.webviewtest.data.model.Notice
import com.example.webviewtest.data.network.NoticeService
import javax.inject.Inject

class NoticeRepository @Inject constructor(private val api: NoticeService) {

    suspend fun getAllNews(): List<Notice> {
        val response = api.getNews()

        //aqui guardar en room

        return response
    }
}