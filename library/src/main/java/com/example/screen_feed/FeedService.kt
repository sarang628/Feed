package com.example.screen_feed

import com.sarang.base_feed.uistate.FeedUiState

interface FeedService {
    suspend fun getFeeds(params: Map<String, String>): List<FeedUiState>
//    suspend fun deleteReview(review: ReviewDeleteRequestVO): Review
//    suspend fun addLike(like: Like): Like
//    suspend fun deleteLike(like: Like): Like
//    suspend fun deleteFavorite(favorite: Favorite): Favorite
//    suspend fun addFavorite(favorite: Favorite): Favorite
}