package com.sarang.torang.usecase

import com.sarang.torang.data.feed.Feed
import kotlinx.coroutines.flow.Flow

interface GetMyFeedFlowUseCase {
    fun invoke(reviewId : Int?): Flow<List<Feed>>
}