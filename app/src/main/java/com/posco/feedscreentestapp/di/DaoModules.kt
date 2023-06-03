package com.posco.feedscreentestapp.di

import com.sryang.torang_repository.data.AppDatabase
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DaoModules {
    @Provides
    fun provideFeedDao(appDatabase: AppDatabase): FeedDao {
        return appDatabase.feedDao()
    }
}