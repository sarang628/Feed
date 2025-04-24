package com.sarang.torang.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sarang.torang.provideFeed
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun TestFeedScreenByReviewId(reviewId: String) {
    var reviewId by remember { mutableIntStateOf(0) }
    Box(Modifier.fillMaxSize()) {
        FeedScreenByReviewId(
            reviewId = reviewId,
            shimmerBrush = { shimmerBrush(it) },
            feed = provideFeed(),
            bottomDetectingLazyColumn = provideBottomDetectingLazyColumn(),
            pullToRefreshLayout = providePullToRefresh(rememberPullToRefreshState())
        )
        SetReviewIdAssistChip(modifier = Modifier.align(Alignment.TopEnd), reviewId) {
            reviewId = it
        }
    }
}