package com.example.screen_feed.uistate

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.screen_feed.adapters.FeedPagerAdapter


data class FeedsUIstate(
    val isRefresh: Boolean = false,
    val isProgess: Boolean = false,
    val feedItemUiState: ArrayList<FeedUiState>? = null,
    val isLogin: Boolean = false,
    val onRefreshListener: SwipeRefreshLayout.OnRefreshListener, //스와이프 레이아웃을 리프레시 할 때 호출되는 이벤트
    val onAddReviewClickListener: Toolbar.OnMenuItemClickListener, // 리뷰를 추가 할 때 호출되는 이벤트
    val reLoad: View.OnClickListener // 갱신 아답터
)

fun FeedsUIstate.isEmptyFeed(): Boolean {
    if (feedItemUiState == null) return true

    return feedItemUiState.isEmpty()
}

fun FeedsUIstate.isVisibleRefreshButton(): Int {
    if (isRefresh) return View.GONE

    return if (this.isEmptyFeed()) View.VISIBLE else View.GONE
}

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