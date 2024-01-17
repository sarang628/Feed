package com.sarang.torang.usecase

import com.sarang.torang.data.feed.Feed
import kotlinx.coroutines.flow.Flow

interface GetFeedFlowUseCase {
    suspend fun invoke(): Flow<List<Feed>>
}