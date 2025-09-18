package com.sarang.torang.compose.feed.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun defaultShimmerBrush(targetValue: Float = 1700f): Brush {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.1f),
            Color.LightGray.copy(alpha = 0.5f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing), repeatMode = RepeatMode.Restart
            ), label = ""
        )

    return Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(
                y = translateAnimation.value - 200,
                x = 0f
            ),
            //end = Offset(x = translateAnimation.value, y = translateAnimation.value)
            end = Offset(
                y = translateAnimation.value - 0,
                x = 0f
            )
        )

}

@Preview
@Composable
fun ShimmerBrushPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(defaultShimmerBrush())
    )
}