package com.sarang.torang.test

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.compose.feed.feedType
import com.sarang.torang.compose.feed.pullToRefreshLayoutType
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.image.provideZoomableTorangAsyncImage
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sarang.torang.provideFeed
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.rememberPullToRefreshState


typealias imageLoadComposeType = @Composable (Modifier, String, Dp?, Dp?, ContentScale?, Dp?) -> Unit

@Composable
fun TestFeedScreenForMain(
    tag: String = "__FeedScreenForMain",
    state: PullToRefreshLayoutState = rememberPullToRefreshState(),
    pullToRefreshLayout: pullToRefreshLayoutType = providePullToRefresh(state),
    onAddReview: () -> Unit = { Log.w(tag, "onAddReview is not implemented") },
    onAlarm: () -> Unit = { Log.w("__FeedScreenForMain", "onAlarm is not implemented") },
    imageLoadCompose: imageLoadComposeType = provideZoomableTorangAsyncImage(),
    feed: feedType = provideFeed(imageLoadCompose = imageLoadCompose),
) {
    var onTop by remember { mutableStateOf(false) }
    FeedScreenInMain(
        scrollToTop = onTop,
        onAlarm = onAlarm,
        onAddReview = onAddReview,
        shimmerBrush = { shimmerBrush(it) },
        onScrollToTop = { onTop = false },
        feed = feed,
        pullToRefreshLayout = pullToRefreshLayout,
        bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()
    )
}