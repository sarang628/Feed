package com.sarang.torang.usecase

import com.sarang.torang.uistate.FeedLoadingUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface GetFeedLodingFlowUseCase {
    fun invoke(coroutineScope: CoroutineScope): StateFlow<FeedLoadingUiState>
}