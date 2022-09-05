package com.posco.feedscreentestapp.di.repositories

import com.sryang.torang_repository.repository.FeedRepository
import com.sryang.torang_repository.repository.impl.FeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedRepositoryModule {
    @Binds
    abstract fun provideFeedRepository(feedRepository: FeedRepositoryImpl): FeedRepository
}