package com.sarang.torang.test

import androidx.compose.runtime.Composable
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sarang.torang.provideFeed
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
private fun TestFeedScreenByRestaurantId(restaurantId: Int) {
    val state = rememberPullToRefreshState()
    FeedScreenByRestaurantId(
        restaurantId = restaurantId,
        shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(),
        pullToRefreshLayout = providePullToRefresh(state),
        bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()
    )
}