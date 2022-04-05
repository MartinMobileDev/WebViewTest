package com.example.webviewtest.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.webviewtest.domain.model.Notice

@Entity(tableName = "notice_table")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "createdAt") var createdAt: String? = null,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "webViewText") var webViewText: String? = null,
    @ColumnInfo(name = "parent_id") var parent_id: Int?,
    @ColumnInfo(name = "active") var active: Boolean? = true
)

fun Notice.toDatabase() =
    NoticeEntity(
        title = title,
        createdAt = createdAt,
        author = author,
        webViewText = webViewText,
        parent_id = parent_id,
        active = active
    )