package com.sryang.torang.usecase

interface DeleteLikeUseCase {
    suspend fun invoke(reviewId: Int)
}