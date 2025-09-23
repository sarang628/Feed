package com.sarang.torang.usecase

import com.sarang.torang.uistate.FeedUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GetFeedFlowUseCase {
    fun invoke(coroutineScope: CoroutineScope): StateFlow<FeedUiState>
}