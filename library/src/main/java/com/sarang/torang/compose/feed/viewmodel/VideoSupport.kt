package com.sarang.torang.compose.feed.viewmodel

interface VideoSupport {
    var videoPlayListState: List<Int>
    fun onVideoClick(reviewId: Int) {
        videoPlayListState = if (videoPlayListState.contains(reviewId)) {
            videoPlayListState - reviewId // 제거
        } else {
            videoPlayListState + reviewId // 추가
        }
    }
}