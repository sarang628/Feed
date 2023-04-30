package com.example.screen_feed.uistate

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

/*피드 하단 UIState*/
data class FeedBottomUIState(
    val reviewId: Int,
    val likeAmount: Int = 0,
    val commentAmount: Int = 0,
    val author: String = "",
    val comment: String = "",
    val isLike: Boolean = false,
    val isFavorite: Boolean = false,
    val onLikeClickListener: ((Int) -> Unit)? = null,
    val onCommentClickListener: ((Int) -> Unit)? = null,
    val onShareClickListener: ((Int) -> Unit)? = null,
    val onClickFavoriteListener: ((Int) -> Unit)? = null,
    val visibleLike: Boolean = false,
    val visibleComment: Boolean = false
)

//피드 하단 테스트 데이터
fun testItemFeedBottomUiState(context: Context, view: View) = FeedBottomUIState(
    reviewId = 0,
    likeAmount = 1,
    commentAmount = 2,
    author = "테스트",
    comment = "4",
    isLike = false,
    isFavorite = false,
    onLikeClickListener = {
        Snackbar.make(context, view, "clickLike", Snackbar.LENGTH_SHORT).show()
    },
    onCommentClickListener = {
        Snackbar.make(context, view, "clickComment", Snackbar.LENGTH_SHORT).show()
    },
    onShareClickListener = {
        Snackbar.make(context, view, "clickShare", Snackbar.LENGTH_SHORT).show()
    },
    onClickFavoriteListener = {
        Snackbar.make(context, view, "clickFavority", Snackbar.LENGTH_SHORT).show()
    },
    visibleLike = true,
    visibleComment = true
)