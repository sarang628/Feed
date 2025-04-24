package com.sarang.torang.test

import androidx.compose.runtime.Composable
import com.sarang.torang.di.image.provideImageLoader
import com.sarang.torang.di.pinchzoom.PinchZoomImageBox

@Composable
fun TestPinchZoom() {
    PinchZoomImageBox(provideImageLoader()) { imageLoader, zoomState ->
        TestFeedScreenForMain(imageLoadCompose = { modifier, url, width, height, contentScale, originHeight ->
            imageLoader.invoke(modifier, url, contentScale, originHeight)
        })
    }
}