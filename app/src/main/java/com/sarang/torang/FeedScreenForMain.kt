package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun FeedScreenForMain() {
    val state = rememberPullToRefreshState()
    var onTop by remember { mutableStateOf(false) }
    com.sarang.torang.compose.feed.FeedScreenForMain(
        onAddReview = {},
        scrollToTop = onTop,
        shimmerBrush = { shimmerBrush(it) },
        onScrollToTop = { onTop = false },
        feed = provideFeed(),
        pullToRefreshLayout = providePullToRefresh(state)
    )
}