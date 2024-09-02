package com.sarang.torang.data.feed

import android.util.Log
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp


private val tag: String = "__FeedImage"

data class FeedImage(
    val url: String,
    val width: Int,
    val height: Int,
)

fun FeedImage.adjustHeight(density: Density, screenWidthDp: Int, screenHeightDp: Int): Int {
    var newHeight = 0
    with(density) {
        val widthDp = width.toDp().value.toInt()
        val heightDp = height.toDp().value.toInt()
        Log.d(tag, "screen resolution : $screenWidthDp * $screenHeightDp")
        Log.d(tag, "picture resolution : $widthDp * $heightDp")




        if (width > 0 && height > 0) {
            if (width > height) {
                // 가로가 더 클 때, 가로 기준으로 크기를 조정
                newHeight = (heightDp * screenWidthDp) / widthDp
                Log.d("__FeedImage", "$heightDp * $screenWidthDp / $widthDp = $newHeight")
            } else {
                // 세로가 더 클 때, 세로 기준으로 크기를 조정
                newHeight = (widthDp * screenHeightDp) / heightDp
                Log.d("__FeedImage", "$widthDp * $screenHeightDp / $heightDp = $newHeight")
            }
        }

        Log.d(tag, "new height : $newHeight")
    }

    return newHeight
}
