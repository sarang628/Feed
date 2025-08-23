package com.sarang.torang.test

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.compose.feed.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.feed.component.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.CustomPullToRefreshType
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun TestFeedScreenForMain(
    tag: String = "__FeedScreenForMain",
    state: PullToRefreshLayoutState = rememberPullToRefreshState(),
    onAddReview: () -> Unit = { Log.w(tag, "onAddReview is not implemented") },
    onAlarm: () -> Unit = { Log.w("__FeedScreenForMain", "onAlarm is not implemented") },
    pageScrollable: Boolean = true
) {
    var onTop by remember { mutableStateOf(false) }
    CompositionLocalProvider(
        LocalFeedCompose provides CustomFeedCompose,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
        LocalPullToRefreshLayoutType provides CustomPullToRefreshType,
        LocalFeedImageLoader provides CustomFeedImageLoader,
        LocalExpandableTextType provides CustomExpandableTextType
    ) {
        FeedScreenInMain(
            scrollToTop = onTop,
            onAlarm = onAlarm,
            onAddReview = onAddReview,
            onScrollToTop = { onTop = false },
            pageScrollable = pageScrollable
        )
    }
}