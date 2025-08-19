package com.sarang.torang.usecase

import com.sarang.torang.data.feed.Feed
import kotlinx.coroutines.flow.Flow

interface GetFeedByRestaurantIdFlowUseCase {
    fun invoke(restaurantId : Int?): Flow<List<Feed>>
}