package com.example.webviewtest.data.model

import com.google.gson.annotations.SerializedName

data class Notice(
    @SerializedName("story_title") var title: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("story_url") var webViewText: String? = null
)
