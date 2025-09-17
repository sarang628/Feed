package com.sarang.torang.compose.feed.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import com.sarang.torang.compose.feed.component.defaultShimmerBrush

typealias ShimmerBrushType = @Composable (Boolean) -> Brush

val LocalShimmerBrush = compositionLocalOf<ShimmerBrushType> {
    @Composable {
        defaultShimmerBrush(it)
    }
}