package com.sarang.torang

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
private fun MyFeedScreen(reviewId: String) {
    val state = rememberPullToRefreshState()
    com.sarang.torang.compose.feed.MyFeedScreen(
        reviewId = try {
            Integer.parseInt(reviewId)
        } catch (e: Exception) {
            0
        },
        shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(),
        onBack = { },
        listState = rememberLazyListState(),
        pullToRefreshLayout = providePullToRefresh(state),
        bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()
    )
}