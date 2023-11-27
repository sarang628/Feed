package com.posco.torang.di.feed

import com.sryang.torang.data1.FeedData
import com.sryang.torang.usecase.AddFavoriteUseCase
import com.sryang.torang.usecase.AddLikeUseCase
import com.sryang.torang.usecase.DeleteFavoriteUseCase
import com.sryang.torang.usecase.DeleteLikeUseCase
import com.sryang.torang.usecase.FeedRefreshUseCase
import com.sryang.torang.usecase.GetFeedFlowUseCase
import com.sryang.torang_repository.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.ConnectException

/**
 * DomainLayer
 * DataLayer 과 UILayer을 연결
 */
@InstallIn(SingletonComponent::class)
@Module
class FeedServiceModule {
    @Provides
    fun provideFeedService(
        feedRepository: FeedRepository
    ): FeedRefreshUseCase {
        return object : FeedRefreshUseCase {
            override suspend fun invoke() {
                try {
                    feedRepository.loadFeed()
                } catch (e: ConnectException) {
                    throw Exception("서버 접속에 실패하였습니다.")
                }
            }
        }
    }

    @Provides
    fun provideAddLikeUsecase(feedRepository: FeedRepository): AddLikeUseCase {
        return object : AddLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.addLike(reviewId)
            }
        }
    }

    @Provides
    fun provideDeleteLikeUsecase(feedRepository: FeedRepository): DeleteLikeUseCase {
        return object : DeleteLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.deleteLike(reviewId)
            }

        }
    }

    @Provides
    fun provideAddFavoriteUseCase(feedRepository: FeedRepository): AddFavoriteUseCase {
        return object : AddFavoriteUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.addFavorite(reviewId)
            }
        }
    }

    @Provides
    fun provideDeleteFavoriteUsecase(feedRepository: FeedRepository): DeleteFavoriteUseCase {
        return object : DeleteFavoriteUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.deleteFavorite(reviewId)
            }
        }
    }

    @Provides
    fun provideGetFeedFlowUseCase(feedRepository: FeedRepository): GetFeedFlowUseCase {
        return object : GetFeedFlowUseCase {
            override suspend fun invoke(): Flow<List<FeedData>> {
                return feedRepository.feeds.map { it ->
                    it.map { it.toFeedData() }
                }
            }
        }
    }
}