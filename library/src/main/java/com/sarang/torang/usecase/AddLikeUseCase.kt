package com.sarang.torang.usecase

interface AddLikeUseCase {
    suspend fun invoke(reviewId: Int)
}