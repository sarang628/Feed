package com.sarang.torang.usecase

import com.sarang.torang.compose.feed.data.Feed
import kotlinx.coroutines.flow.Flow

interface GetFeedByReviewIdUseCase {
    fun invoke(reviewId: Int?): Flow<Feed?>
}