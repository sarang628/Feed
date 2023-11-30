package com.sryang.torang.usecase

import com.sryang.torang.data.feed.FeedData
import kotlinx.coroutines.flow.Flow

interface GetFeedFlowUseCase {
    suspend fun invoke(): Flow<List<FeedData>>
}