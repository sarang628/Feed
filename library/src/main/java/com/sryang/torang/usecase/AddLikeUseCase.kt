package com.sryang.torang.usecase

interface AddLikeUseCase {
    suspend fun invoke(reviewId: Int)
}