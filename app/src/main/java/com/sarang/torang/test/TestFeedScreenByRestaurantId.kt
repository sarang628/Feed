package com.sarang.torang.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.component.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun TestFeedScreenByRestaurantId(restaurantId: Int) {
    var restaurantId by remember { mutableIntStateOf(restaurantId) }
    CompositionLocalProvider(LocalFeedCompose provides CustomFeedCompose,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
    ) {
        Column(Modifier.fillMaxSize()) {
            SetRestaurantIdAssistChip(
                modifier = Modifier,
                restaurantId = restaurantId,
                { restaurantId = it }
            )
            FeedScreenByRestaurantId(
                restaurantId = restaurantId,
                pullToRefreshLayout = providePullToRefresh(rememberPullToRefreshState())
            )
        }
    }
}