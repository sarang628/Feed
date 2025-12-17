package com.sarang.torang.viewmodels

import com.sarang.torang.usecase.ClickLikeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface Likeable {
    fun onLike(
        viewModelScope: CoroutineScope,
        reviewId: Int,
        clickLikeUseCase: ClickLikeUseCase,
        handleErrorMsg: (Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                clickLikeUseCase.invoke(reviewId)
            } catch (e: Exception) {
                handleErrorMsg(e)
            }
        }
    }
}