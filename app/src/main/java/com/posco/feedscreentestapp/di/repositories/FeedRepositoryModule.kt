package com.posco.feedscreentestapp.di.repositories

import com.example.torang_core.repository.FeedRepository
import com.example.torangrepository.test.TestFeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedRepositoryModule {
    @Binds
    abstract fun provideFeedRepository(feedRepository: TestFeedRepositoryImpl): FeedRepository
}