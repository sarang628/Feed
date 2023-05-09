package com.example.screen_feed.uistate

import com.example.screen_feed.data.Feed

/*피드 상단 UIState*/
data class FeedTopUIState(
    val reviewId: Int? = 0,
    val name: String? = "",
    val restaurantName: String? = "",
    val rating: Float? = 0.0f,
    val profilePictureUrl: String? = null,
    val onMenuClickListener: ((Int) -> Unit)? = null,
    val clickProfile: ((Int) -> Unit)? = null,
    val onNameClickListener: ((Int) -> Unit)? = null,
    val onRestaurantClickListener: ((Int) -> Unit)? = null
)

fun Feed.FeedTopUiState(
    onMenuClickListener: ((Int) -> Unit)? = null,
    clickProfile: ((Int) -> Unit)? = null,
    onNameClickListener: ((Int) -> Unit)? = null,
    onRestaurantClickListener: ((Int) -> Unit)? = null
) : FeedTopUIState{
    return FeedTopUIState(
        reviewId = this.reviewId,
        name = this.name,
        restaurantName = this.restaurantName,
        rating = this.rating,
        profilePictureUrl = this.profilePictureUrl,
        onMenuClickListener = onMenuClickListener,
        clickProfile = clickProfile,
        onNameClickListener = onNameClickListener,
        onRestaurantClickListener = onRestaurantClickListener
    )
}