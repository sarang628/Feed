package com.posco.torang.di.feed

import com.sryang.torang.data1.CommentDataUiState
import com.sryang.torang.data1.FeedData
import com.sryang.torang.usecase.FeedService
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
    fun provideFeedService(
        feedRepository: FeedRepository
    ): FeedService {
        return object : FeedService {
            override suspend fun getFeeds() {
                feedRepository.loadFeed()
            }

            override val feeds: Flow<List<FeedData>>
                get() = feedRepository.feeds.map { it ->
                    it.stream().map {
                        it.toFeedData()
                    }.toList()
                }

            override suspend fun addLike(reviewId: Int) {
                feedRepository.addLike(reviewId)
            }

            override suspend fun deleteLike(reviewId: Int) {
                feedRepository.deleteLike(reviewId)
            }

            override suspend fun deleteFavorite(reviewId: Int) {
                feedRepository.deleteFavorite(reviewId)
            }

            override suspend fun addFavorite(reviewId: Int) {
                feedRepository.addFavorite(reviewId)
            }

            override suspend fun getComment(reviewId: Int): CommentDataUiState {
                val result = feedRepository.getComment(reviewId)
                return CommentDataUiState(
                    myProfileUrl = result.profilePicUrl,
                    commentList = result.list.stream().map { it.toCommentData() }.toList()
                )
            }

            override suspend fun addComment(reviewId: Int, comment: String) {
                feedRepository.addComment(reviewId, comment)
            }
        }
    }
}