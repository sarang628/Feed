package com.example.screen_feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.torang_core.data.model.Feed
import com.example.torang_core.dialog.FeedDialogEventAdapter
import com.example.torang_core.dialog.FeedMyDialogEventAdapter
import com.example.torang_core.dialog.NotLoggedInFeedDialogEventAdapter
import com.example.torang_core.repository.FeedRepository
import com.example.torang_core.util.Event
import com.example.torang_core.util.Logger
import com.sarang.base_feed.BaseFeedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(private val feedRepository: FeedRepository) :
    BaseFeedViewModel(feedRepository) {

    /** 데이터가 비어있는지 여부 */
    val empty = MutableLiveData<Boolean>()

    /** 로그인 화면 열기 */
    private val _openLogin = MutableLiveData<Event<Int>>()
    val openLogin: LiveData<Event<Int>> = _openLogin

    /** 리뷰 화면 이동 */
    private val _openAddReview = MutableLiveData<Event<Int>>()
    val openAddReview: LiveData<Event<Int>> = _openAddReview

    /** 로그인 여부 */
    val isLogin = feedRepository.isLogin

    fun clickAddReview() {
        viewModelScope.launch {
            Logger.v("isLogin ${feedRepository.isLogin()}")
            if (feedRepository.isLogin()) {
                _openAddReview.value = Event(0)
            } else {
                _openLogin.value = Event(0)
            }
        }
    }

    // 피드
    fun deleteFeed(reviewId: Int) {
        viewModelScope.launch {
            try {
                feedRepository.deleteFeed(reviewId)
            } catch (e: Exception) {
                _errorMessage.postValue("오류가 발생했습니다:\n$e")
                Logger.e(e.toString())
            }
        }
    }

    init {
        refresh()
    }
}