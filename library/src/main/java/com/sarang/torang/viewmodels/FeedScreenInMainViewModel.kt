package com.sarang.torang.viewmodels

import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.FindFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenInMainViewModel @Inject constructor(
    clickLikeUseCase: ClickLikeUseCase,
    clickFavorityUseCase: ClickFavorityUseCase,
    getFeedLoadingFlowUseCase: GetFeedLodingFlowUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    private val getFeedByRestaurantIdFlowUseCase: GetFeedByRestaurantIdFlowUseCase,
    /**
     * 기존 GetFeedByRestaurantIdFlowUseCase 만 있어서
     * 로컬 DB에서만 피드를 불러와 리뷰를 제대로 못 가져옴.
     * 서버에서 불러오는 로직 추가
     */
    private val findFeedByRestaurantIdFlowUseCase: FindFeedByRestaurantIdFlowUseCase
) : FeedsViewModel(
    feedWithPageUseCase,
    clickLikeUseCase,
    clickFavorityUseCase,
    getFeedLoadingFlowUseCase,
    getFeedFlowUseCase
) {
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
    }
}