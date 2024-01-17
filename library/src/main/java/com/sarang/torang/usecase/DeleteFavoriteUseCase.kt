package com.sarang.torang.usecase

interface DeleteFavoriteUseCase {
    suspend fun invoke(reviewId: Int)

}