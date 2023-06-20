package com.posco.feedscreentestapp.di

import com.sryang.torang_repository.di.service.feed.FeedServiceProductImpl
import com.sryang.torang_repository.services.FeedServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RestaurantServiceModule {
    @Singleton
    @Provides
    fun provideFeedServiceService(
        productFeedServiceImpl: FeedServiceProductImpl
    ): FeedServices {
        return productFeedServiceImpl.create()
    }
}