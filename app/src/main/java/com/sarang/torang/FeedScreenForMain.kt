package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun FeedScreenForMain() {
    val state = rememberPullToRefreshState()
    var onTop by remember { mutableStateOf(false) }
    com.sarang.torang.compose.feed.FeedScreenForMain(
        onAddReview = {},
        onTop = onTop,
        shimmerBrush = { shimmerBrush(it) },
        consumeOnTop = { onTop = false },
        feed = provideFeed(),
        pullToRefreshLayout = providePullToRefresh(state)
    )
}