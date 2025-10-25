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
import com.sarang.torang.usecase.FindFeedByPictureIdFlowUseCase
import com.sarang.torang.usecase.GetFeedByPictureIdFlowUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenByPictureIdViewModel @Inject constructor(
    clickLikeUseCase            : ClickLikeUseCase,
    clickFavoriteUseCase        : ClickFavorityUseCase,
    getLoadingFeedFlowUseCase   : GetFeedLodingFlowUseCase,
    getFeedFlowUseCase          : GetFeedFlowUseCase,
    feedWithPageUseCase         : FeedWithPageUseCase,
    private val getFeed         : GetFeedByPictureIdFlowUseCase,
    /**
     * 기존 GetFeedByRestaurantIdFlowUseCase 만 있어서
     * 로컬 DB에서만 피드를 불러와 리뷰를 제대로 못 가져옴.
     * 서버에서 불러오는 로직 추가
     */
    private val findFeed        : FindFeedByPictureIdFlowUseCase
) : ViewModel(),
    FeedRefreshable,
    InfiniteScrollable,
    ISnackBarMessage,
    VideoSupport{
    private     val tag                 : String                = "__FeedScreenByRestaurantIdViewModel"
                var uiState             : FeedLoadingUiState    by mutableStateOf(FeedLoadingUiState.Loading); internal set
    private     var pictureId           : Int?                  by mutableStateOf(null)
                var isRefreshingState   : Boolean               by mutableStateOf(false);
                var feedUiState         : FeedUiState           by mutableStateOf(FeedUiState()) ; private set
    override    var msgState            : List<String>          by mutableStateOf(listOf())
    override    var videoPlayListState  : List<Int>             by mutableStateOf(listOf())


    fun getFeedByPictureId(pictureId: Int) {
        Log.d(tag, "getFeedByPictureId pictureId : $pictureId")
        this.pictureId = pictureId
        viewModelScope.launch {
            findFeed.invoke(pictureId)
        }
        //TODO:: hot flow로 이해하고 바꾸기
        viewModelScope.launch {
            getFeed.invoke(pictureId).collect {
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
            pictureId?.let {
                findFeed.invoke(it)
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
        Log.d(tag, "onBottom called but nothing to do")
    }

    fun onLike(i: Int) {}
    fun onFavorite(i: Int) {
    }

}