package com.example.screen_feed.usecase

import android.view.View

data class ItemFeedBottomUsecase(
    val clickLikeListener: View.OnClickListener,
    val clickCommentListener: View.OnClickListener,
    val clickShareListener: View.OnClickListener,
    val clickFavoriteListener: View.OnClickListener,
    val likeAmount: Int,
    val commentAmount: Int,
    val author: String,
    val comment: String,
    val isLike : Boolean,
    val isFavorite : Boolean
)
