package com.sryang.torang.usecase

interface AddFavoriteUseCase {
    suspend fun invoke(reviewId: Int)
}