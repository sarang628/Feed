package com.example.screen_feed.uistate

/*피드 하단 UIState*/
data class FeedBottomUIState(
    val reviewId: Int? = 0,
    val likeAmount: Int? = 0,
    val commentAmount: Int? = 0,
    val author: String? = "",
    val author1: String? = "",
    val author2: String? = "",
    val comment: String? = "",
    val comment1: String? = "",
    val comment2: String? = "",
    val isLike: Boolean? = false,
    val isFavorite: Boolean? = false,
    val onLikeClickListener: ((Int) -> Unit)? = null,
    val onCommentClickListener: ((Int) -> Unit)? = null,
    val onShareClickListener: ((Int) -> Unit)? = null,
    val onClickFavoriteListener: ((Int) -> Unit)? = null,
    val visibleLike: Boolean? = false,
    val visibleComment: Boolean? = false,
    val contents : String? = ""
)