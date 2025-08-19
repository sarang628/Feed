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
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.CustomLocalPullToRefreshType
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun TestFeedScreenForMain(
    tag: String = "__FeedScreenForMain",
    state: PullToRefreshLayoutState = rememberPullToRefreshState(),
    onAddReview: () -> Unit = { Log.w(tag, "onAddReview is not implemented") },
    onAlarm: () -> Unit = { Log.w("__FeedScreenForMain", "onAlarm is not implemented") },
) {
    var onTop by remember { mutableStateOf(false) }
    CompositionLocalProvider(LocalFeedCompose provides CustomFeedCompose,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
        LocalPullToRefreshLayoutType provides CustomLocalPullToRefreshType
    ) {
        FeedScreenInMain(
            scrollToTop = onTop,
            onAlarm = onAlarm,
            onAddReview = onAddReview,
            onScrollToTop = { onTop = false },
        )
    }
}