package com.example.screen_feed.uistate

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.screen_feed.adapters.FeedPagerAdapter


/*피드 프레그먼트 UIState*/
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

val FeedUiState.pictureVisible: Boolean get() = reviewImages.isEmpty()

fun FeedUiState.getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
    imageClickListener?.let {
        (pageAdapter as FeedPagerAdapter).setOnImageClickListener(it)
    }
    return pageAdapter

}
