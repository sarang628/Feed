package com.sarang.torang.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefresh
import com.sarang.torang.di.image.provideImageLoader
import com.sarang.torang.di.pinchzoom.PinchZoomImageBox
import com.sarang.torang.di.pinchzoom.isZooming

@Composable
fun TestPinchZoom() {
    PinchZoomImageBox(provideImageLoader()) { imageLoader, zoomState ->
        CompositionLocalProvider(
            LocalFeedImageLoader provides { data -> imageLoader.invoke(data.modifier ,data.url ?: "", data.contentScale, data.height) },
            LocalFeedCompose provides CustomFeedCompose,
            LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
            LocalPullToRefreshLayoutType provides customPullToRefresh,
            LocalExpandableTextType provides CustomExpandableTextType
        ) {
            FeedScreenInMain(pageScrollable = !zoomState.isZooming, scrollEnabled = !zoomState.isZooming)
        }
    }
}