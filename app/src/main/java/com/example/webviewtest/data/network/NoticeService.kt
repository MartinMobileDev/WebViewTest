package com.example.webviewtest.data.network

import com.example.webviewtest.data.model.NoticeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoticeService @Inject constructor(private val api: APIService) {

    suspend fun getNews(): List<NoticeModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getNews("mobile")
            response.body()?.news ?: emptyList()
        }
    }
}