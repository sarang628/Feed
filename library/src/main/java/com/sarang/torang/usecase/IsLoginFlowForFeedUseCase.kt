package com.sarang.torang.usecase

import kotlinx.coroutines.flow.Flow

interface IsLoginFlowForFeedUseCase {
    val isLogin: Flow<Boolean>
}