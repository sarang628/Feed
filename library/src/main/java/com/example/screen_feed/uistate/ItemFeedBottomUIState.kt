package com.example.screen_feed.uistate
data class ItemFeedBottomUIState(
    val reviewId: Int,
    val likeAmount: Int,
    val commentAmount: Int,
    val author: String,
    val comment: String,
    val isLike: Boolean,
    val isFavorite: Boolean,
    val onLikeClickListener: (Int) -> Unit,
    val onCommentClickListener: (Int) -> Unit,
    val onShareClickListener: (Int) -> Unit,
    val onClickFavoriteListener: (Int) -> Unit,
    val visibleLike: Boolean,
    val visibleComment: Boolean
)

