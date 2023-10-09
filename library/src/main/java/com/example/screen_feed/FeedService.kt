package com.example.screen_feed

import kotlinx.coroutines.flow.Flow
import java.util.Objects

interface FeedService {
    suspend fun getFeeds(userId: Int)
    val feeds1: Flow<List<FeedData>>
    suspend fun addLike(userId: Int, reviewId: Int)
    suspend fun deleteLike(userId: Int, reviewId: Int)
    suspend fun deleteFavorite(userId: Int, reviewId: Int)
    suspend fun addFavorite(userId: Int, reviewId: Int)
    suspend fun getComment(reviewId: Int): List<CommentData>
    suspend fun addComment(reviewId: Int, userId: Int, comment: String)
}