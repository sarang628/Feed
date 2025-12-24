package com.sarang.torang.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.compose.feed.component.FeedList
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefresh
import com.sarang.torang.uistate.FeedUiState

@Preview
@Composable
fun FeedListTest(){
    CompositionLocalProvider(
        LocalFeedCompose provides CustomFeedCompose,
        LocalPullToRefreshLayoutType provides customPullToRefresh,
        //LocalExpandableTextType provides CustomExpandableTextType,
        LocalFeedImageLoader provides {CustomFeedImageLoader().invoke(it)},
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
    ) {
        FeedList(
            uiState = FeedUiState(
                list = listOf(Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty,
                              Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty,
                              Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty)
            )
        )
    }
}