package com.sarang.torang.usecase

import com.sarang.torang.compose.feed.FeedUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface GetFeedFlowUseCase {
    fun invoke(coroutineScope: CoroutineScope): StateFlow<FeedUiState>
}