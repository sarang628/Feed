package com.sarang.torang

import androidx.compose.runtime.Composable
import com.sarang.torang.di.feed_di.provideBottonDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun FeedScreenByReviewId(reviewId: String) {
    val state = rememberPullToRefreshState()
    com.sarang.torang.compose.feed.FeedScreenByReviewId(
        reviewId = Integer.parseInt(reviewId),
        shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(),
        pullToRefreshLayout = providePullToRefresh(state),
        bottomDetectingLazyColumn = provideBottonDetectingLazyColumn()
    )
}