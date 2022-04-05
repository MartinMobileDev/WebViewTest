package com.example.webviewtest

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WebViewTest : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: WebViewTest? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        applicationContext()
    }
}