package com.example.webviewtest.data.network

import com.example.webviewtest.data.model.ResponseNotices
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("search_by_date")
    suspend fun getNews(@Query("query") query: String): Response<ResponseNotices>
}