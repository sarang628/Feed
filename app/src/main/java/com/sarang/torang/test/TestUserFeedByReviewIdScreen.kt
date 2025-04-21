package com.sarang.torang.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.UserFeedByReviewIdScreen
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sarang.torang.provideFeed
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
internal fun TestUserFeedByReviewIdScreen() {
    var reviewId by remember { mutableIntStateOf(415) }
    Box(Modifier.fillMaxSize())
    {
        UserFeedByReviewIdScreen(
            reviewId = reviewId,
            shimmerBrush = { shimmerBrush(it) },
            feed = provideFeed(),
            onBack = { },
            listState = rememberLazyListState(),
            pullToRefreshLayout = providePullToRefresh(rememberPullToRefreshState()),
            bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()
        )

        AssistChip(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = { },
            label = {
                Text(text = "MyFeed reviewId:")
                BasicTextField(value = reviewId.toString(), onValueChange = {
                    try {
                        reviewId = it.toInt()
                    } catch (e: Exception) {

                    }
                })
            })
    }
}