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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.ConnectException
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

    open val uiState: StateFlow<FeedUiState> = getFeedFlowUseCase.invoke(viewModelScope)
    var msgState : List<String> by mutableStateOf(listOf()); private set
    var focusedIndexState by mutableIntStateOf(0); private set
    var isRefreshingState by mutableStateOf(false);
    var videoPlayListState : List<Int> by mutableStateOf(listOf())

    private fun handleErrorMsg(e: Exception) { e.message?.let{showError(it)} }
    private fun showError(msg: String) { this.msgState = this.msgState + msg }
    fun onFocusItemIndex(index: Int) { focusedIndexState = index }

    init {
        viewModelScope.launch {
            feedWithPageUseCase.invoke(0)
            page = 1
        }
    }

    // 피드 리스트 갱신
    open fun refreshFeed() {
        viewModelScope.launch {
            isRefreshingState = true
            try { feedWithPageUseCase.invoke(0); page = 1 }
            catch (e: ConnectException) { if(page == 0)  handleErrorMsg(e) }
            catch (e: Exception) { if(page == 0) handleErrorMsg(e) }
            finally { isRefreshingState = false }
        }
    }

    // 즐겨찾기 클릭
    internal fun onFavorite(reviewId: Int) {
        viewModelScope.launch {
            try {
                if (uiState.isLogin) { clickFavoriteUseCase.invoke(reviewId) }
                else{ throw Exception("로그인을 해주세요.") }
            }
            catch (e: Exception) { handleErrorMsg(e) }
        }
    }

    // 좋아여 클릭
    internal fun onLike(reviewId: Int) {
        viewModelScope.launch {
            try {
                if (uiState.isLogin) { clickLikeUseCase.invoke(reviewId) }
                else{ throw Exception("로그인을 해주세요.") }
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
            }
            catch (e: ConnectException) { handleErrorMsg(e) }
            catch (e: Exception) { handleErrorMsg(e) }
        }
    }

    fun onVideoClick(reviewId: Int) {
        videoPlayListState = if (videoPlayListState.contains(reviewId)) {
            videoPlayListState - reviewId // 제거
        } else {
            videoPlayListState + reviewId // 추가
        }
    }

    fun removeTopErrorMessage() {
        if (msgState.isNotEmpty())
            msgState = msgState.drop(0)
    }
}

val StateFlow<FeedUiState>.isLogin : Boolean get() = when(this.value){
    FeedUiState.Empty -> false
    is FeedUiState.Error -> false
    FeedUiState.Loading -> false
    FeedUiState.Reconnect -> false
    is FeedUiState.Success -> (this.value as FeedUiState.Success).isLogin
}