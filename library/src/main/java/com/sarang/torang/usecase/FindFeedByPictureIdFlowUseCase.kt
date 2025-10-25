package com.sarang.torang.usecase

interface FindFeedByPictureIdFlowUseCase {
    suspend fun invoke(restaurantId : Int)
}