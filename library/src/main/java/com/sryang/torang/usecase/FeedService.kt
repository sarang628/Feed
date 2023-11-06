package com.sryang.torang.usecase

import com.sryang.torang.data.CommentDataUiState
import com.sryang.torang.data.FeedData
import kotlinx.coroutines.flow.Flow

interface FeedService {
    suspend fun getFeeds()
    val feeds: Flow<List<FeedData>>
    suspend fun addLike(reviewId: Int)
    suspend fun deleteLike(reviewId: Int)
    suspend fun deleteFavorite(reviewId: Int)
    suspend fun addFavorite(reviewId: Int)
    suspend fun getComment(reviewId: Int): CommentDataUiState
    suspend fun addComment(reviewId: Int, comment: String)
}