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
    val reviewImages: List<FeedImage> = listOf(),
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

        val Sample = Feed(
            reviewId = 0,
            restaurantId = 0,
            userId = 0,
            name = "userName",
            restaurantName = "restaurantName",
            rating = 3.5f,
            profilePictureUrl = "http://sarang628.iptime.org:89/profile_images/4/2024-08-15/11_16_37_583.jpeg",
            likeAmount = 100,
            commentAmount = 150,
            isLike = false,
            isFavorite = false,
            contents = "contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents contents",
            createDate = "createDate",
            reviewImages = listOf(FeedImage.Sample)
        )
    }
}

val Feed.visibleLike: Boolean get() = this.likeAmount > 0