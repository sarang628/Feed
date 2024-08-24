package com.sarang.torang.usecase

/**
 * 선택한 리뷰를 작성한 사용자의 모든 리뷰 가져오기
 * 프로필 하단 이미지들을 선택했을 때 사용자 리뷰 목록으로 이동하기 위해
 */
interface GetMyAllFeedByReviewIdUseCase {
    suspend fun invoke(reviewId: Int)
}