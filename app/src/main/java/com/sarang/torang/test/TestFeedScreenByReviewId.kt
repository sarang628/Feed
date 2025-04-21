package com.sarang.torang.test

import androidx.compose.runtime.Composable
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sarang.torang.provideFeed
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun TestFeedScreenByReviewId(reviewId: String) {
    val state = rememberPullToRefreshState()
    FeedScreenByReviewId(
        reviewId = Integer.parseInt(reviewId),
        shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(),
        pullToRefreshLayout = providePullToRefresh(state),
        bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()
    )
}