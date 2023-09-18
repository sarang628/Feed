package com.example.screen_feed

import com.sryang.library.entity.Feed

interface FeedService {
    suspend fun getFeeds(params: Map<String, String>): List<Feed>
//    suspend fun deleteReview(review: ReviewDeleteRequestVO): Review
//    suspend fun addLike(like: Like): Like
//    suspend fun deleteLike(like: Like): Like
//    suspend fun deleteFavorite(favorite: Favorite): Favorite
//    suspend fun addFavorite(favorite: Favorite): Favorite
}