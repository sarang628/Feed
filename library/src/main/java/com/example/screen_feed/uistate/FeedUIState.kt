package com.example.screen_feed.uistate

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.screen_feed.adapters.FeedPagerAdapter


data class FeedFragmentUIstate(
    val isRefresh: Boolean = false,
    val isProgess: Boolean = false,
    val feedItemUiState: ArrayList<FeedUiState>? = null,
    val isLogin: Boolean = false,
    val onRefreshListener: SwipeRefreshLayout.OnRefreshListener, //스와이프 레이아웃을 리프레시 할 때 호출되는 이벤트
    val onAddReviewClickListener: Toolbar.OnMenuItemClickListener, // 리뷰를 추가 할 때 호출되는 이벤트
    val reLoad: View.OnClickListener // 갱신 아답터
)

fun FeedFragmentUIstate.isEmptyFeed(): Boolean {
    if (feedItemUiState == null) return true

    return feedItemUiState.isEmpty()
}

fun FeedFragmentUIstate.isVisibleRefreshButton(): Int {
    if (isRefresh) return View.GONE

    return if (this.isEmptyFeed()) View.VISIBLE else View.GONE
}

data class FeedUiState(
    val itemId: Long,
    val itemFeedTopUiState: FeedTopUIState? = null,
    val itemFeedBottomUiState: FeedBottomUIState? = null,
    val reviewImages: ArrayList<String> = ArrayList(),
    val visibleReviewImage: Boolean = false,
    val pageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null,
    val imageClickListener: ((Int) -> Unit)? = null
)

val FeedUiState.pictureVisible: Boolean get() = reviewImages.isEmpty()

fun FeedUiState.getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
    imageClickListener?.let {
        (pageAdapter as FeedPagerAdapter).setOnImageClickListener(it)
    }
    return pageAdapter

}

data class FeedTopUIState(
    val reviewId: Int,
    val name: String = "",
    val restaurantName: String = "",
    val rating: Float = 0.0f,
    val profilePictureUrl: String? = null,
    val onMenuClickListener: ((Int) -> Unit)? = null,
    val onProfileImageClickListener: ((Int) -> Unit)? = null,
    val onNameClickListener: ((Int) -> Unit)? = null,
    val onRestaurantClickListener: ((Int) -> Unit)? = null
)

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