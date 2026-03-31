package com.sarang.torang.compose.feed

import androidx.compose.ui.unit.Density
import com.sarang.torang.compose.feed.data.Feed
import com.sarang.torang.compose.feed.data.adjustHeight

data class FeedUiState(
    val list: List<Feed> = listOf(),
    val isLogin : Boolean = false
)


/** 첫번째 이미지 크기 계산 */
fun FeedUiState.imageHeight(
    density: Density,
    screenWidthDp: Int,
    screenHeightDp: Int
): Int {
    if (list.isEmpty() || list[0].reviewImages.isEmpty())
        return 0
    else
        return list[0].reviewImages[0].adjustHeight(density, screenWidthDp, screenHeightDp)
}