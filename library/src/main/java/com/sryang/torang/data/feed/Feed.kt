package com.sryang.torang.data.feed

data class Feed(
    val reviewId: Int,
    val restaurantId: Int,
    val userId: Int,
    val name: String,
    val restaurantName: String,
    val rating: Float,
    val profilePictureUrl: String,
    val likeAmount: Int,
    val commentAmount: Int,
    val isLike: Boolean,
    val isFavorite: Boolean,
    val contents: String,
    val reviewImages: List<String> = ArrayList()
)

val Feed.visibleLike: Boolean get() = this.likeAmount > 0