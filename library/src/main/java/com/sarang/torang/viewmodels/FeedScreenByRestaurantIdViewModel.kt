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
class FeedScreenByRestaurantIdViewModel @Inject constructor(
    clickLikeUseCase: ClickLikeUseCase,
    clickFavoriteUseCase: ClickFavorityUseCase,
    getLoadingFeedFlowUseCase: GetFeedLodingFlowUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    private val getFeedByRestaurantIdFlowUseCase: GetFeedByRestaurantIdFlowUseCase,
    /**
     * 기존 GetFeedByRestaurantIdFlowUseCase 만 있어서
     * 로컬 DB에서만 피드를 불러와 리뷰를 제대로 못 가져옴.
     * 서버에서 불러오는 로직 추가
     */
    private val findFeedByRestaurantIdFlowUseCase: FindFeedByRestaurantIdFlowUseCase
) : ViewModel(),
    FeedRefreshable,
    InfiniteScrollable,
    ISnackBarMessage,
    VideoSupport{
    private val tag = "__FeedScreenByRestaurantIdViewModel"
    var uiState: FeedLoadingUiState by mutableStateOf(FeedLoadingUiState.Loading); internal set
    private var restaurantId : Int? by mutableStateOf(null)
    var isRefreshingState by mutableStateOf(false);
    var feedUiState: FeedUiState by mutableStateOf(FeedUiState()) ; private set
    override var msgState : List<String> by mutableStateOf(listOf())
    override var videoPlayListState : List<Int> by mutableStateOf(listOf())


    fun getFeedByRestaurantId(restaurantId: Int) {
        Log.d(tag, "getFeedByRestaurantId : $restaurantId")
        this.restaurantId = restaurantId
        viewModelScope.launch {
            findFeedByRestaurantIdFlowUseCase.invoke(restaurantId)
        }
        //TODO:: hot flow로 이해하고 바꾸기
        viewModelScope.launch {
            getFeedByRestaurantIdFlowUseCase.invoke(restaurantId).collect {
                feedUiState = it

                if(it.list.isNotEmpty())
                    uiState = FeedLoadingUiState.Success
            }
        }
    }

    fun findIndexByReviewId(list: List<Feed>, reviewId: Int): Int {
        return list.indexOf(list.find { it.reviewId == reviewId })
    }

    override fun refreshFeed() {
        isRefreshingState = true
        viewModelScope.launch {
            restaurantId?.let {
                findFeedByRestaurantIdFlowUseCase.invoke(it)
            }
            isRefreshingState = false
        }
    }

    // 피드 리스트 갱신
    override fun reconnect() {
        viewModelScope.launch {
            //uiState = FeedLoadingUiState.Loading
        }
    }

    override fun onBottom() {

    }
}