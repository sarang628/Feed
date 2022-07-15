package com.example.screen_feed.usecase

import android.view.View

data class ItemFeedTopUseCase(
    val name: String,
    val restaurantName : String,
    val rating : Float,
    val profilePictureUrl : String,
    val onMenuClickListener : View.OnClickListener,
    val onProfileImageClickListener : View.OnClickListener,
    val onNameClickListener : View.OnClickListener,
    val onRestaurantClickListener : View.OnClickListener
)