package com.example.screen_feed.uistate

import com.example.screen_feed.data.Feed

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
    val contents: String? = ""
)

fun Feed.FeedBottomUIState(
    onLikeClickListener: ((Int) -> Unit)? = null,
    onCommentClickListener: ((Int) -> Unit)? = null,
    onShareClickListener: ((Int) -> Unit)? = null,
    onClickFavoriteListener: ((Int) -> Unit)? = null
): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        likeAmount = this.likeAmount,
        commentAmount = this.commentAmount,
        author = this.author,
        author1 = this.author1,
        author2 = this.author2,
        comment = this.comment,
        comment1 = this.comment1,
        comment2 = this.comment2,
        isLike = this.isLike,
        isFavorite = this.isFavorite,
        contents = this.contents,
        onLikeClickListener = onLikeClickListener,
        onCommentClickListener = onCommentClickListener,
        onShareClickListener = onShareClickListener,
        onClickFavoriteListener = onClickFavoriteListener
    )
}