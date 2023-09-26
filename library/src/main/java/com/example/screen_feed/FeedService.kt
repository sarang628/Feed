package com.example.screen_feed

import com.sarang.base_feed.uistate.FeedUiState
import kotlinx.coroutines.flow.Flow

interface FeedService {
    suspend fun getFeeds(params: Map<String, String>)
    val feeds : Flow<List<FeedUiState>>
    val feeds1 : Flow<List<FeedUiState>>
//    suspend fun deleteReview(review: ReviewDeleteRequestVO): Review
    suspend fun addLike(reviewId: Int)
    suspend fun deleteLike(reviewId: Int)
    suspend fun deleteFavorite(reviewId: Int)
    suspend fun addFavorite(reviewId: Int)
}