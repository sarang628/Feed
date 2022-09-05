package com.posco.feedscreentestapp.di.service

import com.posco.feedscreentestapp.BuildConfig
import com.sryang.torang_repository.services.FeedServices
import com.sryang.torang_repository.services.feed.LocalFeedServiceImpl
import com.sryang.torang_repository.services.feed.ProductFeedServiceImpl
import com.sryang.torang_repository.services.feed.TestFeedServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FeedServiceModule {

    @Singleton
    @Provides
    fun provideFeedService(
        feedProductFeedService: ProductFeedServiceImpl,
        localFeedService: LocalFeedServiceImpl,
        testFeedServices: TestFeedServiceImpl
    ): FeedServices {
        return when (BuildConfig.feedService) {
            "test" -> testFeedServices
            "local" -> localFeedService.create()
            "real" -> feedProductFeedService.create()
            else
            -> feedProductFeedService.create()
        }
    }
}
