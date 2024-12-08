package com.sarang.torang

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun FeedScreenForMain(
    state: PullToRefreshLayoutState = rememberPullToRefreshState(),
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = providePullToRefresh(
        state
    ),
    onAddReview: (() -> Unit) = {
        Log.w(
            "__FeedScreenForMain",
            "onAddReview is not implemented"
        )
    },
    onAlarm: () -> Unit = { Log.w("__FeedScreenForMain", "onAlarm is not implemented") },
    feed: @Composable ((
        feed: Feed,
        onLike: (Int) -> Unit,
        onFavorite: (Int) -> Unit,
        isLogin: Boolean,
        onVideoClick: () -> Unit,
        imageHeight: Int,
    ) -> Unit) = provideFeed()
) {
    var onTop by remember { mutableStateOf(false) }
    com.sarang.torang.compose.feed.FeedScreenForMain(
        scrollToTop = onTop,
        onAlarm = onAlarm,
        onAddReview = onAddReview,
        shimmerBrush = { shimmerBrush(it) },
        onScrollToTop = { onTop = false },
        feed = feed,
        pullToRefreshLayout = pullToRefreshLayout
    )
}