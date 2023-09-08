package com.posco.feedscreentestapp.di

import com.example.screen_feed.FeedService
import com.sarang.base_feed.data.Feed
import com.sryang.torang_repository.data.AppDatabase
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.dao.PictureDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Provides
    fun provideFeedService(): FeedService {
        return object : FeedService {
            override suspend fun getFeeds(params: Map<String, String>): List<Feed> {
                return ArrayList()
            }
        }
    }
}