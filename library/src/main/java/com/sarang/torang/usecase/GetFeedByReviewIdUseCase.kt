package com.sarang.torang.usecase

import com.sarang.torang.data.feed.Feed

interface GetFeedByReviewIdUseCase {
    suspend fun invoke(reviewId: Int): Feed
}