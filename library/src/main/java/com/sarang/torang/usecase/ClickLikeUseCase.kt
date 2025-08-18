package com.sarang.torang.usecase

interface ClickLikeUseCase {
    suspend fun invoke(reviewId: Int)
}