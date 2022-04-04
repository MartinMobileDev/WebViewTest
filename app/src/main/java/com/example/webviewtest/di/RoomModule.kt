package com.example.webviewtest.di

import android.content.Context
import androidx.room.Room
import com.example.webviewtest.data.database.NoticeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val NOTICE_DATABASE_NAME = "notice_database"

    @Singleton
    @Provides
    fun providesRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NoticeDatabase::class.java, NOTICE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providesNoticeDao(db: NoticeDatabase) = db.getNoticeDao()
}