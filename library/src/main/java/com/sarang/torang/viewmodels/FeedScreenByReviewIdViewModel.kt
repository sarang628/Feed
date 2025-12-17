package com.sarang.torang.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.GetFeedByReviewIdUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import com.sarang.torang.usecase.LoadFeedByReviewIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenByReviewIdViewModel @Inject constructor(
    private val getFeedByReviewIdUseCase: GetFeedByReviewIdUseCase,
    private val loginFlowForFeedUseCase: IsLoginFlowForFeedUseCase,
    private val clickFavoriteUseCase: ClickFavorityUseCase,
    private val clickLikeUseCase: ClickLikeUseCase,
    private val loadByReviewIdUseCase: LoadFeedByReviewIdUseCase
) : ViewModel(),
    Likeable,
    Favoritable,
    ISnackBarMessage{
    private val tag = "__MyFeedsViewModel"
    var feedUiState : FeedUiState by mutableStateOf(FeedUiState()); private set
    var feedLoadUiState : FeedLoadingUiState by mutableStateOf(FeedLoadingUiState.Loading); private set
    override var msgState : List<String> by mutableStateOf(listOf());

    init {
        viewModelScope.launch {
            loginFlowForFeedUseCase.isLogin.collect {
                feedUiState.copy(isLogin = it)
            }
        }
    }

    private fun handleErrorMsg(e: Exception) { e.message?.let{showError(it)} }

    internal fun showError(msg: String) { this.msgState = this.msgState + msg }

    fun getUserFeedByReviewId(reviewId: Int) {
        viewModelScope.launch {
            try {
                loadByReviewIdUseCase.invoke(reviewId = reviewId)
            }catch (e : Exception){
                handleErrorMsg(e)
            }
        }
        viewModelScope.launch {
            try {
                combine(getFeedByReviewIdUseCase.invoke(reviewId), loginFlowForFeedUseCase.isLogin){
                    feed, isLogin ->
                    FeedUiState(if(feed == null) listOf() else listOf(feed), isLogin)
                }.collect {
                    feedLoadUiState = FeedLoadingUiState.Success
                    feedUiState = it
                }
            }catch (e : Exception){
                handleErrorMsg(e)
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

    // 좋아여 클릭
    fun onLike(reviewId: Int) {
        onLike(viewModelScope   = viewModelScope,
               clickLikeUseCase = clickLikeUseCase,
               reviewId         = reviewId,
               handleErrorMsg   = { handleErrorMsg(it) } )
    }

    fun onVideoClick(reviewId: Int) {

    }

    fun onFavorite(reviewId: Int) {
        onFavorite(viewModelScope       = viewModelScope,
                   reviewId             = reviewId,
                   clickFavoriteUseCase = clickFavoriteUseCase,
                   handleErrorMsg       = { handleErrorMsg(it) })
    }
}