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
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.FindFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class FeedScreenInMainViewModel @Inject constructor(
    val feedWithPageUseCase: FeedWithPageUseCase,
    val clickLikeUseCase: ClickLikeUseCase,
    val clickFavoriteUseCase: ClickFavorityUseCase,
    getFeedLoadingFlowUseCase: GetFeedLodingFlowUseCase,
    val getFeedFlowUseCase: GetFeedFlowUseCase,
    private val getFeedByRestaurantIdFlowUseCase: GetFeedByRestaurantIdFlowUseCase,
    /**
     * 기존 GetFeedByRestaurantIdFlowUseCase 만 있어서
     * 로컬 DB에서만 피드를 불러와 리뷰를 제대로 못 가져옴.
     * 서버에서 불러오는 로직 추가
     */
    private val findFeedByRestaurantIdFlowUseCase: FindFeedByRestaurantIdFlowUseCase
) : ViewModel(),
    InfiniteScrollable,
    FeedRefreshable,
    FocusByIndex,
    VideoSupport,
    Likeable,
    Favoritable
{
    private val tag = "__FeedScreenInMainViewModel"
    internal var page = 0

    var uiState: FeedLoadingUiState by mutableStateOf(FeedLoadingUiState.Loading); internal set
    var feedUiState by mutableStateOf(FeedUiState()); private set
    var isRefreshingState by mutableStateOf(false);
    override var focusedIndexState by mutableIntStateOf(0)
    override var videoPlayListState : List<Int> by mutableStateOf(listOf())
    var msgState : List<String> by mutableStateOf(listOf()); private set

    init {
        viewModelScope.launch {
            try {
                feedWithPageUseCase.invoke(0)
                page = 1
            }catch (e : Exception){
                showError(e.message ?: "")
                uiState = FeedLoadingUiState.Reconnect
            }
        }

        viewModelScope.launch {
            getFeedFlowUseCase.invoke(viewModelScope).collect {
                feedUiState = it

                if(it.list.isNotEmpty()){
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
    internal fun showError(msg: String) { this.msgState = this.msgState + msg }

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
}