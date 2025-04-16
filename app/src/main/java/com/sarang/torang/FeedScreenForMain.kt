package com.sarang.torang

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.feed_di.provideBottonDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.image.provideZoomableTorangAsyncImage
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
    imageLoadCompose: @Composable (Modifier, String, Dp?, Dp?, ContentScale?, Dp?) -> Unit = provideZoomableTorangAsyncImage(),
    feed: @Composable ((
        feed: Feed,
        onLike: (Int) -> Unit,
        onFavorite: (Int) -> Unit,
        isLogin: Boolean,
        onVideoClick: () -> Unit,
        imageHeight: Int,
    ) -> Unit) = provideFeed(imageLoadCompose = imageLoadCompose),
) {
    var onTop by remember { mutableStateOf(false) }
    com.sarang.torang.compose.feed.FeedScreenForMain(
        scrollToTop = onTop,
        onAlarm = onAlarm,
        onAddReview = onAddReview,
        shimmerBrush = { shimmerBrush(it) },
        onScrollToTop = { onTop = false },
        feed = feed,
        pullToRefreshLayout = pullToRefreshLayout,
        bottomDetectingLazyColumn = provideBottonDetectingLazyColumn()
    )
}