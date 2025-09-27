package com.sarang.torang.usecase

import com.sarang.torang.uistate.FeedUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GetFeedByRestaurantIdFlowUseCase {
    fun invoke(restaurantId : Int?): Flow<FeedUiState>
}