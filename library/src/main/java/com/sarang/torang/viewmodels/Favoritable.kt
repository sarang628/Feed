package com.sarang.torang.viewmodels

import com.sarang.torang.usecase.ClickFavorityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface Favoritable {
    fun onFavorite(reviewId: Int,
                   viewModelScope : CoroutineScope,
                   clickFavoriteUseCase : ClickFavorityUseCase,
                   handleErrorMsg : (Exception)->Unit = {}){
        viewModelScope.launch {
            try {
                clickFavoriteUseCase.invoke(reviewId)
            }
            catch (e: Exception) { handleErrorMsg(e) }
        }
    }
}