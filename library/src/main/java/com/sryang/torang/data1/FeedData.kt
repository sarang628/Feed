package com.sryang.torang.data1

data class FeedData(
    val reviewId: Int,
    val restaurantId: Int,
    val userId: Int,
    val name: String,
    val restaurantName: String,
    val rating: Float,
    val profilePictureUrl: String,
    val likeAmount: Int,
    val commentAmount: Int,
    val author: String,
    val author1: String,
    val author2: String,
    val comment: String,
    val comment1: String,
    val comment2: String,
    val isLike: Boolean,
    val isFavorite: Boolean,
    val visibleLike: Boolean,
    val visibleComment: Boolean,
    val contents: String,
    val reviewImages: List<String> = ArrayList()
)
