package com.sarang.torang.usecase

import com.sarang.torang.data.feed.Feed
import kotlinx.coroutines.flow.Flow

interface GetFeedByReviewIdUseCase {
    fun invoke(reviewId: Int?): Flow<Feed?>
}