package com.sarang.torang.uistate

import com.sarang.torang.data.feed.Feed

data class FeedUiState(
    val isRefreshing: Boolean = false   // 스크롤 리프레시
    , val list: List<Feed> = listOf()   // 피드 리스트
    , val error: String? = null         // 에러 메시지
)