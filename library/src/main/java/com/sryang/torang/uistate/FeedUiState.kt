package com.sryang.torang.uistate

import com.sryang.torang.data.CommentData
import com.sryang.torang.data.FeedData

data class FeedUiState(
    val isRefreshing: Boolean = false                   // 스크롤 리프레시
    , val list: List<FeedData> = ArrayList()            // 피드 리스트
    , val showFeedMenuDialog: Boolean = false           // 피드메뉴 팝업
    , val showCommentDialog: Boolean = false            // 커멘트 팝업
    , val showShareDialog: Boolean = false              // 공유 팝업
    , val showReportDialog: Boolean = false             // 신고 팝업
    , val isFailedLoadFeed: Boolean = false             // 피드 로딩 실패
    , val selectedReviewId: Int? = null                 // 선택한 리뷰
    , val comments: List<CommentData>? = null           // 커멘트 리스트
    , val myProfileUrl: String? = null                  // 커멘트 하단에 사용할 프로필 url
    , val error: String? = null                         // 에러 메시지
)