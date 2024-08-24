package com.sarang.torang

import androidx.compose.runtime.Composable
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
private fun FeedScreenByReviewId(reviewId: String) {
    val state = rememberPullToRefreshState()
    com.sarang.torang.compose.feed.FeedScreenByReviewId(
        reviewId = Integer.parseInt(reviewId),
        shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(),
        pullToRefreshLayout = providePullToRefresh(state)
    )
}