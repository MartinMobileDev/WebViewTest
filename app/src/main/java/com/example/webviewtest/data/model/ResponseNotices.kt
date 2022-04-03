package com.example.webviewtest.data.model

import com.google.gson.annotations.SerializedName


data class ResponseNotices (

    @SerializedName("hits" ) var news : ArrayList<Notice> = arrayListOf()

)