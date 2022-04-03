package com.example.webviewtest.data.network

import com.example.webviewtest.data.model.Notice
import com.example.webviewtest.utils.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoticeService @Inject constructor(private val api: APIService) {

    suspend fun getNews(): List<Notice> {
        return withContext(Dispatchers.IO) {
            val response = api.getNews("mobile")
            response.body()?.news ?: emptyList()
        }
    }
}