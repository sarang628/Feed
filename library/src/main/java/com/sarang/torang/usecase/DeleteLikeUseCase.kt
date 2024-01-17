package com.sarang.torang.usecase

interface DeleteLikeUseCase {
    suspend fun invoke(reviewId: Int)
}