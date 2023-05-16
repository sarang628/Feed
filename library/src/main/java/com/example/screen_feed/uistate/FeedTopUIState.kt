package com.example.screen_feed.uistate

import com.example.screen_feed.data.Feed

/*피드 상단 UIState*/
data class FeedTopUIState(
    val reviewId: Int? = 0,
    val name: String? = "",
    val restaurantName: String? = "",
    val rating: Float? = 0.0f,
    val profilePictureUrl: String? = null
)

fun Feed.FeedTopUiState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        name = this.name,
        restaurantName = this.restaurantName,
        rating = this.rating,
        profilePictureUrl = this.profilePictureUrl
    )
}