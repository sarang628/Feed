package com.sarang.torang.compose.feed.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush

typealias ShimmerBrushType = @Composable (Boolean) -> Brush

val LocalShimmerBrush = compositionLocalOf<ShimmerBrushType> {
    @Composable {
        defaultShimmerBrush(it)
    }
}