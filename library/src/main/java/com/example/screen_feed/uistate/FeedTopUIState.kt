package com.example.screen_feed.uistate

/*피드 상단 UIState*/
data class FeedTopUIState(
    val reviewId: Int? = 0,
    val name: String? = "",
    val restaurantName: String? = "",
    val rating: Float? = 0.0f,
    val profilePictureUrl: String? = null,
    val onMenuClickListener: ((Int) -> Unit)? = null,
    val onProfileImageClickListener: ((Int) -> Unit)? = null,
    val onNameClickListener: ((Int) -> Unit)? = null,
    val onRestaurantClickListener: ((Int) -> Unit)? = null
)