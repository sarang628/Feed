package com.sarang.torang.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
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
    val feedWithPageUseCase: FeedWithPageUseCase,
    val clickLikeUseCase: ClickLikeUseCase,
    val clickFavoriteUseCase: ClickFavorityUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
) : ViewModel() {
    private val tag = "__FeedsViewModel"
    private var page = 0

    val isLoginState = isLoginFlowUseCase.isLogin
    open val uiState: StateFlow<FeedUiState> = getFeedFlowUseCase.invoke()
        .map<List<Feed>, FeedUiState>(FeedUiState::Success)
        .onStart { emit(FeedUiState.Loading) }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = FeedUiState.Loading)

    var msgState : String? by mutableStateOf(null); private set
    var focusedIndexState by mutableIntStateOf(0); private set
    var isRefreshingState by mutableStateOf(false);  private set
    var videoPlayListState : List<Int> by mutableStateOf(listOf())

    private fun handleErrorMsg(e: Exception) { showError(e.message) }
    private fun showError(msg: String?) { this.msgState = msg }
    fun clearErrorMsg() { msgState = null } // 에러메시지 삭제
    fun onFocusItemIndex(index: Int) { focusedIndexState = index }

    // 피드 리스트 갱신
    open fun refreshFeed() {
        viewModelScope.launch {
            isRefreshingState = true
            try { page = 0; feedWithPageUseCase.invoke(page); page++ }
            catch (e: Exception) { handleErrorMsg(e) }
            finally { isRefreshingState = false }
        }
    }

    // 즐겨찾기 클릭
    internal fun onFavorite(reviewId: Int) {
        viewModelScope.launch {
            try {
                if (isLoginState.stateIn(viewModelScope).value)
                    clickFavoriteUseCase.invoke(reviewId)
            }
            catch (e: Exception) { handleErrorMsg(e) }
        }
    }

    // 좋아여 클릭
    internal fun onLike(reviewId: Int) {
        viewModelScope.launch {
            try {
                if (isLoginState.stateIn(viewModelScope).value)
                    clickLikeUseCase.invoke(reviewId)
            }
            catch (e: Exception) { handleErrorMsg(e) }
        }
    }

    open fun onBottom() {
        viewModelScope.launch {
            try {
                Log.d(tag, "called onBottom. request $page pages.")
                feedWithPageUseCase.invoke(page)
                page++
            } catch (e: Exception) { handleErrorMsg(e) }
        }
    }

    fun onVideoClick(reviewId: Int) {
        videoPlayListState = if (videoPlayListState.contains(reviewId)) {
            videoPlayListState - reviewId // 제거
        } else {
            videoPlayListState + reviewId // 추가
        }
    }
}