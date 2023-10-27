package com.sryang.torang.uistate

import com.sryang.torang.data.CommentData
import com.sryang.torang.data.FeedData

data class FeedUiState(
    val isRefreshing: Boolean = false                   // 스크롤 리프레시
    , val list: List<FeedData> = ArrayList()            // 피드 리스트
    , val isExpandMenuBottomSheet: Boolean = false      // 피드메뉴 show 유무
    , val isExpandCommentBottomSheet: Boolean = false   // 커멘트창 show 유무
    , val isShareCommentBottomSheet: Boolean = false    // 공유창 show 유무
    , val isFailedLoadFeed: Boolean = false             // 피드 로딩 실패
    , val selectedReviewId: Int? = null                 // 선택한 리뷰
    , val comments: List<CommentData>? = null           // 커멘트 리스트
    , val myProfileUrl: String? = null                  // 커멘트 하단에 사용할 프로필 url
    , val error: String? = null                         // 에러 메시지
)