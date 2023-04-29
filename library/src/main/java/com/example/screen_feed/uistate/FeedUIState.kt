package com.example.screen_feed.uistate

import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.adapters.FeedPagerAdapter


data class FeedsUIstate(
    val isRefresh: Boolean = false,
    val isProgess: Boolean = false,
    val isEmptyFeed: Boolean,
    val toastMsg: String? = null,
    val feedItemUiState: ArrayList<FeedUiState>? = null,
    val isLogin: Boolean = false,
    val errorMsg: String = ""
)

data class FeedUiState(
    val itemId: Long,
    val itemFeedTopUiState: FeedTopUIState,
    val itemFeedBottomUiState: FeedBottomUIState,
    val reviewImages: ArrayList<String>,
    val visibleReviewImage: Boolean = false,
    val pageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    val imageClickListener: (Int) -> Unit
)

val FeedUiState.pictureVisible: Boolean get() = reviewImages.isEmpty()

fun FeedUiState.getAdapter(): FeedPagerAdapter {
    (pageAdapter as FeedPagerAdapter).setOnImageClickListener(imageClickListener)
    return pageAdapter
}

data class FeedTopUIState(
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

data class FeedBottomUIState(
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