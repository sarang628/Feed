package com.example.screen_feed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.toItemTimeLineUIState
import com.example.torang_core.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class TestFeedsViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _feedsUiState = MutableStateFlow(
        FeedsUIstate(
            isRefresh = false,
            isEmptyFeed = true
        )
    )

    val feedsUiState: StateFlow<FeedsUIstate> = _feedsUiState

    /** 로그인 화면 열기 */
    //private val _openLogin = MutableLiveData<Event<Int>>()
    //val openLogin: LiveData<Event<Int>> = _openLogin

    /** 리뷰 화면 이동 */
    //private val _openAddReview = MutableLiveData<Event<Int>>()
    //val openAddReview: LiveData<Event<Int>> = _openAddReview

    /** 로그인 여부 */
    val isLogin = false /*feedRepository.isLogin*/

    fun clickAddReview() {
        viewModelScope.launch {
            if(!isLogin) {
                _feedsUiState.update { it.copy(goLogin = true) }
            }
            else {
                _feedsUiState.update { it.copy(toastMsg = "clickAddReview") }
            }
        }
        viewModelScope.launch {
            _feedsUiState.update { it.copy(toastMsg = null) }
        }
        /*viewModelScope.launch {
            Logger.v("isLogin ${feedRepository.isLogin()}")
            if (feedRepository.isLogin()) {
                _openAddReview.value = Event(0)
            } else {
                _openLogin.value = Event(0)
            }
        }*/
    }

    // 피드
    fun deleteFeed(reviewId: Int) {
        /*viewModelScope.launch {
            try {
                feedRepository.deleteFeed(reviewId)
            } catch (e: Exception) {
                _errorMessage.postValue("오류가 발생했습니다:\n$e")
                Logger.e(e.toString())
            }
        }*/
    }

    fun reload() {
        viewModelScope.launch {
            _feedsUiState.update {
                it.copy(isRefresh = true)
            }

            val list = feedRepository.loadFeed()
                .stream().map { it.toItemTimeLineUIState() }
                .collect(Collectors.toList())

            delay(1000)
            _feedsUiState.update {
                it.copy(
                    isRefresh = false,
                    isEmptyFeed = !it.isEmptyFeed,
                    feedItemUiState = ArrayList(list)
                )
            }
        }
    }

    fun clickLike(reviewId : Int) {
        TODO("Not yet implemented")
    }

    fun clickFavorite(reviewId : Int) {
        TODO("Not yet implemented")
    }

    fun consumeGoLogin() {
        viewModelScope.launch {
            _feedsUiState.update {
                it.copy(goLogin = false)
            }
        }
    }
}