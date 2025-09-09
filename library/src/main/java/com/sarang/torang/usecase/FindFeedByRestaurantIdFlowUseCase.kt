package com.sarang.torang.usecase

interface FindFeedByRestaurantIdFlowUseCase {
    suspend fun invoke(restaurantId : Int)
}