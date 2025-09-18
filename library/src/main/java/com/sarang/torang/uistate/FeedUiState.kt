package com.sarang.torang.uistate

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.adjustHeight
import java.util.Objects

// @formatter:off
sealed interface FeedUiState {
    data class  Error   (val msg: String?)                     : FeedUiState
    data class  Success (val list: List<Feed> = listOf())    : FeedUiState
    object      Loading                                     : FeedUiState
    object      Empty                                       : FeedUiState
    object      Reconnect                                   : FeedUiState
}
// @formatter:on

/** 첫번째 이미지 크기 계산 */
fun FeedUiState.Success.imageHeight(
    density: Density,
    screenWidthDp: Int,
    screenHeightDp: Int
): Int {
    if (list.isEmpty() || list[0].reviewImages.isEmpty())
        return 0
    else
        return list[0].reviewImages[0].adjustHeight(density, screenWidthDp, screenHeightDp)
}