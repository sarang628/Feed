package com.sarang.torang.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.GetFeedByReviewIdUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenByReviewIdViewModel @Inject constructor(
    val getFeedByReviewIdUseCase: GetFeedByReviewIdUseCase,
    val loginFlowForFeedUseCase: IsLoginFlowForFeedUseCase
) : ViewModel() {
    private val tag = "__MyFeedsViewModel"
    var feedUiState : FeedUiState by mutableStateOf(FeedUiState()); private set
    var feedLoadUiState : FeedLoadingUiState by mutableStateOf(FeedLoadingUiState.Loading); private set

    init {
        viewModelScope.launch {
            loginFlowForFeedUseCase.isLogin.collect {
                feedUiState.copy(isLogin = it)
            }
        }
    }

    fun getUserFeedByReviewId(reviewId: Int) {
        viewModelScope.launch {
            try {
                feedLoadUiState = FeedLoadingUiState.Success
                feedUiState = feedUiState.copy(
                    list = listOf(getFeedByReviewIdUseCase.invoke(reviewId)),
                    isLogin = false
                )
            }catch (e : Exception){
                Log.e(tag, e.toString())
            }
        }
    }

    fun refreshFeed() {

    }

    fun onBottom() {
    }

    fun findIndexByReviewId(list: List<Feed>, reviewId: Int): Int {
        return list.indexOf(list.find { it.reviewId == reviewId })
    }

    fun onLike(it: Int) {

    }

    fun onVideoClick(reviewId: Int) {

    }

    fun onFavorite(it: Int) {

    }
}