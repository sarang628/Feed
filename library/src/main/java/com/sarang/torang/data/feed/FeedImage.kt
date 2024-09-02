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
        val widthDp = width.toDp().value.toInt() // 이미지 가로 크기
        val heightDp = height.toDp().value.toInt() // 이미지 세로 크기

        val imageAspectRatio = widthDp.toFloat() / heightDp.toFloat()
        val screenAspectRatio = screenWidthDp.toFloat() / screenHeightDp.toFloat()

        Log.d(tag, "screen resolution : $screenWidthDp * $screenHeightDp")
        Log.d(tag, "picture resolution : $widthDp * $heightDp")




        if (width > 0 && height > 0) {
            if (imageAspectRatio > screenAspectRatio) {
                // 이미지가 화면보다 더 넓은 경우: 가로를 기준으로 크기를 맞춤
                //newWidth = screenWidthDp
                newHeight = (screenWidthDp / imageAspectRatio).toInt()
            } else {
                // 이미지가 화면보다 더 높은 경우: 세로를 기준으로 크기를 맞춤
                newHeight = screenHeightDp
                //newWidth = (screenHeightDp * imageAspectRatio).toInt()
            }

        }

        Log.d(tag, "new height : $newHeight")
    }

    return newHeight
}
