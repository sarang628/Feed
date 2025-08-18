package com.sarang.torang.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.UserFeedByReviewIdScreen
import com.sarang.torang.compose.feed.component.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
internal fun TestUserFeedByReviewIdScreen() {
    var reviewId by remember { mutableIntStateOf(415) }
    CompositionLocalProvider(LocalFeedCompose provides CustomFeedCompose,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
    ) {
        Box(Modifier.fillMaxSize())
        {
            UserFeedByReviewIdScreen(
                reviewId = reviewId,
                shimmerBrush = { shimmerBrush(it) },
                onBack = { },
                listState = rememberLazyListState(),
                pullToRefreshLayout = providePullToRefresh(rememberPullToRefreshState()),
            )

            SetReviewIdAssistChip(modifier = Modifier.align(Alignment.TopEnd), reviewId) {
                reviewId = it
            }
        }
    }
}