package com.sarang.torang.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
open class FeedsViewModel @Inject constructor(
    val feedWithPageUseCase: FeedWithPageUseCase,
    val clickLikeUseCase: ClickLikeUseCase,
    val clickFavoriteUseCase: ClickFavorityUseCase,
    getLoadingFeedFlowUseCase: GetFeedLodingFlowUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
) : ViewModel(),
    InfiniteScrollable,
    FeedRefreshable,
    FocusByIndex,
    VideoSupport,
    Likeable,
    Favoritable,
    ISnackBarMessage{
    private val tag = "__FeedsViewModel"
    internal var page = 0

    var uiState: FeedLoadingUiState by mutableStateOf(FeedLoadingUiState.Loading); internal set
    var feedUiState by mutableStateOf(FeedUiState()); private set
    override var msgState : List<String> by mutableStateOf(listOf());
    override var focusedIndexState by mutableIntStateOf(0);
    var isRefreshingState by mutableStateOf(false);
    override var videoPlayListState : List<Int> by mutableStateOf(listOf())

    private fun handleErrorMsg(e: Exception) { e.message?.let{showError(it)} }
    internal fun showError(msg: String) { this.msgState = this.msgState + msg }

    init {
        viewModelScope.launch {
            getFeedFlowUseCase.invoke(viewModelScope).collect {
                feedUiState = it
                if (it.list.isNotEmpty())
                    uiState = FeedLoadingUiState.Success
            }
        }
        refreshFeed()
    }

    // 피드 리스트 갱신
    override fun refreshFeed() {
        viewModelScope.launch {
            isRefreshingState = true
            try { feedWithPageUseCase.invoke(0); page = 1 }
            catch (e: ConnectException) { if(page == 0)  handleErrorMsg(e) }
            catch (e: Exception) { if(page == 0) handleErrorMsg(e) }
            finally { isRefreshingState = false }
        }
    }

    // 피드 리스트 갱신
    override fun reconnect() {
        viewModelScope.launch {
            uiState = FeedLoadingUiState.Loading
            isRefreshingState = true
            try { feedWithPageUseCase.invoke(0); page = 1 }
            catch (e: ConnectException) { if(page == 0) { uiState = FeedLoadingUiState.Reconnect }; handleErrorMsg(e) }
            catch (e: Exception) { if(page == 0){ uiState = FeedLoadingUiState.Reconnect }; handleErrorMsg(e) }
            finally { isRefreshingState = false }
        }
    }

    // 즐겨찾기 클릭
    override fun onFavorite(reviewId: Int) {
        viewModelScope.launch {
            try {
                if (feedUiState.isLogin) { clickFavoriteUseCase.invoke(reviewId) }
                else{ throw Exception("로그인을 해주세요.") }
            }
            catch (e: Exception) { handleErrorMsg(e) }
        }
    }

    // 좋아여 클릭
    override fun onLike(reviewId: Int) {
        viewModelScope.launch {
            try {
                if (feedUiState.isLogin) { clickLikeUseCase.invoke(reviewId) }
                else{ throw Exception("로그인을 해주세요.") }
            }
            catch (e: Exception) { handleErrorMsg(e) }
        }
    }

    override fun onBottom() {
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
}