package com.sarang.torang.usecase

interface FeedWithPageUseCase {
    suspend fun invoke(page: Int)
}