package com.sarang.torang.usecase

interface ClickFavorityUseCase {
    suspend fun invoke(reviewId: Int)
}