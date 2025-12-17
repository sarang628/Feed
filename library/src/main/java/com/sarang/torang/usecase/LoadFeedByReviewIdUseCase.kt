package com.sarang.torang.usecase

interface LoadFeedByReviewIdUseCase {
    suspend fun invoke(reviewId: Int)
}