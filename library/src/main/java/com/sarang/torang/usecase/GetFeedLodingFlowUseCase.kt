package com.sarang.torang.usecase

import com.sarang.torang.compose.feed.FeedLoadingUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface GetFeedLodingFlowUseCase {
    fun invoke(coroutineScope: CoroutineScope): StateFlow<FeedLoadingUiState>
}