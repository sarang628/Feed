package com.example.screen_feed.uistate

data class ItemFeedTopUIState(
    val reviewId: Int,
    val name: String,
    val restaurantName: String,
    val rating: Float,
    val profilePictureUrl: String,
    val onMenuClickListener: (Int) -> Unit,
    val onProfileImageClickListener: (Int) -> Unit,
    val onNameClickListener: (Int) -> Unit,
    val onRestaurantClickListener: (Int) -> Unit
)