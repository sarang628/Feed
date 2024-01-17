package com.sarang.torang.usecase

interface AddFavoriteUseCase {
    suspend fun invoke(reviewId: Int)
}