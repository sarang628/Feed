package com.sarang.torang.compose.feed.component

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
            Color.Gray.copy(alpha = 1.0f),
            Color.LightGray.copy(alpha = 0.0f),
            Color.Gray.copy(alpha = 1.0f),
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
                y = translateAnimation.value - 5000,
                x = 0f
            ),
            //end = Offset(x = translateAnimation.value, y = translateAnimation.value)
            end = Offset(
                y = translateAnimation.value + 5000,
                x = 0f
            )
        )

}

@Composable
fun rememberShimmerBrush(targetValue: Float = 1700f): Brush {

    isSystemInDarkTheme()

    val shimmerColors =
        if (!isSystemInDarkTheme()) {
            listOf(
                Color.LightGray.copy(alpha = 0.5f),
                Color.LightGray.copy(alpha = 0.3f),
                Color.LightGray.copy(alpha = 0.5f),
            )
        } else {
            listOf(
                Color.LightGray.copy(alpha = 0.5f),
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.5f),
            )
        }

    // 🌈 transition은 한 번만 생성됨
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    // ✨ 매 프레임 브러시 재생성 대신 translateAnim 값만 바뀜
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = 0f, y = translateAnim.value-800),
        end = Offset(x = 0f, y = translateAnim.value-0)
    )
}

@Preview
@Composable
fun ShimmerBrushPreview() {
    val shimmer = rememberShimmerBrush()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(shimmer)
    )
}