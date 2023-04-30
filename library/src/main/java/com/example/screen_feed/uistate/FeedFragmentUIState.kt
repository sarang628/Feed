package com.example.screen_feed.uistate

import android.content.Context
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.screen_feed.adapters.FeedPagerAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


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

//-------------------------------------------------------------------------------------------
fun getTestSenarioFeedFragmentUIstate(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    view: View
): StateFlow<FeedFragmentUIstate> {
    val data = MutableStateFlow(getTestEmptyFeedFragmentUIstate())

    lifecycleOwner.lifecycleScope.launch {
        data.emit(
            data.value.copy(
                isRefresh = true,
                feedItemUiState = null
            )
        )
        delay(2000)
        data.emit(getTestFeedList(data.value, context, view))
    }
    return data
}

fun getTestEmptyFeedFragmentUIstate(): FeedFragmentUIstate {
    return FeedFragmentUIstate(
        isRefresh = false,
        isProgess = false,
        feedItemUiState = null,
        isLogin = false,
        reLoad = {},
        onAddReviewClickListener = { false },
        onRefreshListener = {}
    )
}

fun getTestFeedList(
    uiState: FeedFragmentUIstate,
    context: Context,
    view: View
): FeedFragmentUIstate {
    return uiState.copy(
        isRefresh = false,
        feedItemUiState = arrayListOf(
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view),
            testItemFeedUiState(context, view)
        )
    )
}