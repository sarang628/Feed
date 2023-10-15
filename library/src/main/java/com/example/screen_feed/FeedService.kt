package com.example.screen_feed

import kotlinx.coroutines.flow.Flow
import java.util.Objects

interface FeedService {
    suspend fun getFeeds()
    val feeds1: Flow<List<FeedData>>
    suspend fun addLike(reviewId: Int)
    suspend fun deleteLike(reviewId: Int)
    suspend fun deleteFavorite(reviewId: Int)
    suspend fun addFavorite(reviewId: Int)
    suspend fun getComment(reviewId: Int): List<CommentData>
    suspend fun addComment(reviewId: Int, comment: String)
}