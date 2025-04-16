package com.sarang.torang.data.feed

import androidx.compose.ui.unit.Density

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

        if (width > 0 && height > 0) {
            if (imageAspectRatio > screenAspectRatio) {
                newHeight =
                    (screenWidthDp / imageAspectRatio).toInt() // 이미지가 화면보다 더 넓은 경우: 가로를 기준으로 크기를 맞춤
            } else {
                newHeight = screenHeightDp // 이미지가 화면보다 더 높은 경우: 세로를 기준으로 크기를 맞춤
            }
        }
    }

    return newHeight
}
