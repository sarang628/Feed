package com.example.screen_feed

data class FeedsScreenInputEvents(
    val onRefresh: (() -> Unit), // 스와이프 리프레시 이벤트
    val onProfile: ((Int) -> Unit), // 프로필 이미지 클릭
    val onRestaurant: ((Int) -> Unit), // 식당명 클릭
    val onImage: ((Int) -> Unit), // 이미지 클릭
    val onMenu: (() -> Unit), // 피드 메뉴 클릭
    val onName: (() -> Unit), // 이름 클릭
    val onAddReview: ((Int) -> Unit), // 리뷰 추가 클릭
    val onLike: ((Int) -> Unit), // 좋아요 클릭
    val onComment: ((Int) -> Unit), // 코멘트 클릭
    val onShare: ((Int) -> Unit), // 공유 클릭
    val onFavorite: ((Int) -> Unit) // 즐겨찾기 클릭
)