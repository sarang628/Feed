package com.sarang.torang.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.DeleteAllFeedUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class FeedScreenInMainViewModel @Inject constructor(
    private val feedWithPageUseCase: FeedWithPageUseCase,
    private val clickLikeUseCase: ClickLikeUseCase,
    private val clickFavoriteUseCase: ClickFavorityUseCase,
    private val deleteAllFeed: DeleteAllFeedUseCase,
    private val getFeedFlowUseCase: GetFeedFlowUseCase,
) : ViewModel(),
    InfiniteScrollable,
    FeedRefreshable,
    FocusByIndex,
    VideoSupport,
    Likeable,
    Favoritable
{
    private val tag = "__FeedScreenInMainViewModel"
    private var page = 0

    var uiState: FeedLoadingUiState by mutableStateOf(FeedLoadingUiState.Loading); internal set
    var feedUiState by mutableStateOf(FeedUiState()); private set
    var isRefreshingState by mutableStateOf(false); private set
    override var focusedIndexState by mutableIntStateOf(0)
    override var videoPlayListState : List<Int> by mutableStateOf(listOf())
    var msgState : List<String> by mutableStateOf(listOf()); private set

    init {
        viewModelScope.launch {
            loadPage()
            subScribeFeed()
        }
    }

    fun loadPage(){
        viewModelScope.launch {
            try {
                feedWithPageUseCase.invoke(0)
                page = 1
            }catch (e : Exception){
                showError(e.message ?: "")
                uiState = FeedLoadingUiState.Reconnect
            }
        }
    }

    fun deleteAllFeed(){
        viewModelScope.launch {
            deleteAllFeed.invoke()
        }
    }

    fun subScribeFeed(){
        viewModelScope.launch {
            getFeedFlowUseCase.invoke(viewModelScope).collect {
                feedUiState = it

                if(it.list.isNotEmpty()){
                    delay(1000)
                    uiState = FeedLoadingUiState.Success
                }
            }
        }
    }

    fun removeTopErrorMessage() {
        if (msgState.isNotEmpty())
            msgState = msgState.drop(0)
    }

    private fun handleErrorMsg(e: Exception) { e.message?.let{showError(it)} }
    private fun showError(msg: String) { this.msgState += msg }

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

    // 피드 리스트 갱신
    override fun refreshFeed() {
        viewModelScope.launch {
            isRefreshingState = true
            delay(500)
            try { feedWithPageUseCase.invoke(0); page = 1 }
            catch (e: ConnectException) { if(page == 0)  handleErrorMsg(e) }
            catch (e: Exception) { if(page == 0) handleErrorMsg(e) }
            finally { isRefreshingState = false }
        }
    }

    override fun onVideoClick(reviewId: Int) {
        videoPlayListState = if (videoPlayListState.contains(reviewId)) {
            videoPlayListState - reviewId // 제거
        } else {
            videoPlayListState + reviewId // 추가
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

    fun onLike(reviewId: Int) {
        onLike(viewModelScope   = viewModelScope,
               clickLikeUseCase = clickLikeUseCase,
               reviewId         = reviewId)
    }

    fun onFavorite(reviewId: Int) {
        onFavorite(viewModelScope       = viewModelScope,
                   reviewId             = reviewId,
                   clickFavoriteUseCase = clickFavoriteUseCase,
                   handleErrorMsg       = { handleErrorMsg(it) })
    }
}