package com.sarang.torang.data.feed

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
    val reviewImages: List<String> = ArrayList(),
    val onProfile: (() -> Unit)? = null,
    val onLike: (() -> Unit)? = null,
    val onComment: (() -> Unit)? = null,
    val onShare: (() -> Unit)? = null,
    val onFavorite: (() -> Unit)? = null,
    val onMenu: (() -> Unit)? = null,
    val onName: (() -> Unit)? = null,
    val onRestaurant: (() -> Unit)? = null,
    val onImage: ((Int) -> Unit)? = null,
)

val Feed.visibleLike: Boolean get() = this.likeAmount > 0