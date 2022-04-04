package com.example.webviewtest.domain.model

import com.example.webviewtest.data.database.entities.NoticeEntity
import com.example.webviewtest.data.model.NoticeModel

data class Notice(
    var title: String? = null,
    var createdAt: String? = null,
    var author: String? = null,
    var webViewText: String? = null)

fun NoticeModel.toDomain() = Notice(title, createdAt, author, webViewText)
fun NoticeEntity.toDomain() = Notice(title, createdAt, author, webViewText)
