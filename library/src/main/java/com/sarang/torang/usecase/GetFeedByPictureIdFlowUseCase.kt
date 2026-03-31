package com.sarang.torang.usecase

import com.sarang.torang.compose.feed.FeedUiState
import kotlinx.coroutines.flow.Flow

interface GetFeedByPictureIdFlowUseCase {
    fun invoke(restaurantId : Int?): Flow<FeedUiState>
}