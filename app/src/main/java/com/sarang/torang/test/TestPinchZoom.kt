package com.sarang.torang.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.compose.feed.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.feed.component.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.CustomPullToRefreshType
import com.sarang.torang.di.image.provideImageLoader
import com.sarang.torang.di.pinchzoom.PinchZoomImageBox
import com.sarang.torang.di.pinchzoom.isZooming

@Composable
fun TestPinchZoom() {
    PinchZoomImageBox(provideImageLoader()) { imageLoader, zoomState ->
        CompositionLocalProvider(
            LocalFeedImageLoader provides { m,url,w,h,scale,s-> imageLoader.invoke(m,url,scale,s) },
            LocalFeedCompose provides CustomFeedCompose,
            LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
            LocalPullToRefreshLayoutType provides CustomPullToRefreshType,
            LocalExpandableTextType provides CustomExpandableTextType
        ) {
            FeedScreenInMain(pageScrollable = !zoomState.isZooming, scrollEnabled = !zoomState.isZooming)
        }
    }
}