package com.sarang.torang.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefresh

@Composable
fun TestFeedScreenByRestaurantId(restaurantId: Int) {
    var restaurantId: String by remember { mutableStateOf(restaurantId.toString()) }
    CompositionLocalProvider(
        LocalFeedCompose provides CustomFeedCompose,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
        LocalPullToRefreshLayoutType provides customPullToRefresh
    ) {
        Column(Modifier.fillMaxSize()) {
            SetRestaurantIdAssistChip(
                modifier = Modifier,
                restaurantId = restaurantId,
                { restaurantId = it }
            )

            val restaurantId =  try {
                restaurantId.toInt()
            } catch (e: Exception) {
                -1
            }

            if (restaurantId > 0)
                FeedScreenByRestaurantId(restaurantId = restaurantId)
        }
    }
}