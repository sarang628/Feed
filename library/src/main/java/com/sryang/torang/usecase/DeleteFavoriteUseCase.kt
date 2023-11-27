package com.sryang.torang.usecase

interface DeleteFavoriteUseCase {
    suspend fun invoke(reviewId: Int)

}