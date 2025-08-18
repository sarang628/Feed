package com.sarang.torang.viewmodels

import android.util.Log
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FeedsViewModel @Inject constructor(
    private val feedWithPageUseCase: FeedWithPageUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFeedFlowUseCase: GetFeedFlowUseCase,
    private val isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
) : ViewModel() {
    var uiState: StateFlow<FeedUiState> = getFeedFlowUseCase.invoke()
        .map<List<Feed>, FeedUiState>(FeedUiState::Success)
        .onStart { emit(FeedUiState.Loading) }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = FeedUiState.Loading)
    private var page = 0
    val isLogin = isLoginFlowUseCase.isLogin

    val msg: String? = null
    val focusedIndex: Int = 0

    var isRefreshing by mutableStateOf(false)
        private set

    open val tag = "__FeedsViewModel"

    // 피드 리스트 갱신
    open fun refreshFeed() {
        viewModelScope.launch {
            isRefreshing = true
            try {
                page = 0
                feedWithPageUseCase.invoke(page)
                page++
            } catch (e: Exception) {
                handleErrorMsg(e)
            } finally {
                isRefreshing = false
            }
        }
    }

    // 즐겨찾기 클릭
    internal fun onFavorite(reviewId: Int) {
        viewModelScope.launch {
            val review = (uiState as FeedUiState.Success).list.find { it.reviewId == reviewId }
            review?.let {
                try {
                    if (it.isFavorite) {
                        deleteFavoriteUseCase.invoke(reviewId)
                    } else {
                        addFavoriteUseCase.invoke(reviewId)
                    }
                } catch (e: Exception) {
                    handleErrorMsg(e)
                }
            }
        }
    }

    // 좋아여 클릭
    internal fun onLike(reviewId: Int) {
        onLike(uiState as FeedUiState.Success, reviewId)
    }

    // 좋아여 클릭
    internal fun onLike(uiState: FeedUiState.Success, reviewId: Int) {
        uiState.list.find { it.reviewId == reviewId }?.let {
            try {
                if (it.isLike) {
                    viewModelScope.launch { deleteLikeUseCase.invoke(reviewId) }
                } else {
                    viewModelScope.launch { addLikeUseCase.invoke(reviewId) }
                }
            } catch (e: Exception) {
                handleErrorMsg(e)
            }
        }
    }

    private fun handleErrorMsg(e: Exception) {
        showError(e.message)
    }

    private fun showError(msg: String?) {
        //uiState = (uiState as FeedUiState.Success).copy(msg = msg)
    }

    // 에러메시지 삭제
    fun clearErrorMsg() {
        //uiState = (uiState as FeedUiState.Success).copy(msg = null)
    }

    open fun onBottom() {
        viewModelScope.launch {
            try {
                Log.d(tag, "called onBottom. request $page pages.")
                feedWithPageUseCase.invoke(page)
                page++
            } catch (e: Exception) {
                handleErrorMsg(e)
            } finally {
            }
        }
    }

    fun onVideoClick(reviewId: Int) {
        //(uiState as FeedUiState.Success).let {
            //uiState = it.copy(list = it.list.map { if (it.reviewId == reviewId) it.copy(isPlaying = !it.isPlaying) else it })
        //} TODO:: 설정하기
    }

    fun onFocusItemIndex(index: Int) {
        //(uiState as FeedUiState.Success).let {
            //uiState = it.copy(focusedIndex = index)
        //} TODO:: 설정하기
    }
}