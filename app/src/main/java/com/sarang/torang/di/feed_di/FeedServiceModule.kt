package com.sarang.torang.di.feed_di

import com.sarang.torang.data.feed.Feed
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedRefreshUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sryang.torang_repository.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DomainLayer
 * DataLayer 과 UILayer을 연결
 */
@InstallIn(SingletonComponent::class)
@Module
class FeedServiceModule {
    @Provides
    fun provideFeedRefreshUseCase(
        feedRepository: FeedRepository
    ): FeedRefreshUseCase {
        return object : FeedRefreshUseCase {
            override suspend fun invoke() {
                feedRepository.loadFeed()
            }
        }
    }

    @Provides
    fun provideAddLikeUseCase(
        feedRepository: FeedRepository
    ): AddLikeUseCase {
        return object : AddLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.addLike(reviewId)
            }
        }
    }

    @Provides
    fun provideDeleteLikeUseCase(
        feedRepository: FeedRepository
    ): DeleteLikeUseCase {
        return object : DeleteLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.deleteLike(reviewId)
            }
        }
    }

    @Provides
    fun provideAddFavoriteUseCase(
        feedRepository: FeedRepository
    ): AddFavoriteUseCase {
        return object : AddFavoriteUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.addFavorite(reviewId)
            }
        }
    }

    @Provides
    fun provideDeleteFavoriteUseCase(
        feedRepository: FeedRepository
    ): DeleteFavoriteUseCase {
        return object : DeleteFavoriteUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.deleteFavorite(reviewId)
            }
        }
    }

    @Provides
    fun provideGetFeedFlowUseCase(
        feedRepository: FeedRepository
    ): GetFeedFlowUseCase {
        return object : GetFeedFlowUseCase {
            override suspend fun invoke(): Flow<List<Feed>> {
                return feedRepository.feeds.map {
                    it.map { it.toFeedData() }
                }
            }
        }
    }
}