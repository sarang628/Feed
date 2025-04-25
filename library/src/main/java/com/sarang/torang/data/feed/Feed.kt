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
    val createDate: String,
    val reviewImages: List<FeedImage> = ArrayList(),
    val onProfile: (() -> Unit)? = null,
    val onLike: (() -> Unit)? = null,
    val onComment: (() -> Unit)? = null,
    val onShare: (() -> Unit)? = null,
    val onFavorite: (() -> Unit)? = null,
    val onMenu: (() -> Unit)? = null,
    val onName: (() -> Unit)? = null,
    val onRestaurant: (() -> Unit)? = null,
    val onImage: ((Int) -> Unit)? = null,
    val isPlaying: Boolean = true,
) {
    companion object {
        val Empty = Feed(
            reviewId = 0,
            restaurantId = 0,
            userId = 0,
            name = "",
            restaurantName = "",
            rating = 0f,
            profilePictureUrl = "",
            likeAmount = 0,
            commentAmount = 0,
            isLike = false,
            isFavorite = false,
            contents = "",
            createDate = ""
        )
    }
}

val Feed.visibleLike: Boolean get() = this.likeAmount > 0