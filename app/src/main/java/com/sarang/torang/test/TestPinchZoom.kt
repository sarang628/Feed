package com.sarang.torang.test

import androidx.compose.runtime.Composable

@Composable
fun TestPinchZoom() {
    /*PinchZoomImageBox(provideImageLoader()) { imageLoader, zoomState ->
        CompositionLocalProvider(
            LocalFeedImageLoader provides { data -> imageLoader.invoke(data.modifier ,data.url ?: "", data.contentScale, data.height) },
            LocalFeedCompose provides CustomFeedCompose,
            LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
            LocalPullToRefreshLayoutType provides customPullToRefresh,
            LocalExpandableTextType provides CustomExpandableTextType
        ) {
            FeedScreenInMain(pageScrollable = !zoomState.isZooming, scrollEnabled = !zoomState.isZooming)
        }
    }*/
}