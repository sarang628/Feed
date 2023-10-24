package com.sryang.torang.uistate

import com.sryang.torang.data.CommentData
import com.sryang.torang.data.FeedData

data class FeedUiState(
    val isRefreshing: Boolean = false,
    val list: List<FeedData> = ArrayList(),
    val isExpandMenuBottomSheet: Boolean = false,
    val isExpandCommentBottomSheet: Boolean = false,
    val isShareCommentBottomSheet: Boolean = false,
    val isFailedLoadFeed: Boolean = false,
    val selectedReviewId: Int? = null,
    val comments: List<CommentData>? = null,
    val myProfileUrl: String? = null,
    val error: String? = null
)