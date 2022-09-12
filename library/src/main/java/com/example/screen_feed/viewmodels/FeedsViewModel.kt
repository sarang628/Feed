package com.example.screen_feed.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.ItemFeedBottomUIState
import com.example.screen_feed.usecase.ItemFeedTopUIState
import com.example.screen_feed.usecase.ItemFeedUIState
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sryang.torang_core.data.entity.Feed
import com.sryang.torang_core.util.Logger
import com.sryang.torang_repository.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    val TAG = "FeedsViewModel"

    private val _feedsUiState = MutableStateFlow(
        FeedsUIstate(
            isRefresh = false,
            isEmptyFeed = true
        )
    )

    val feedsUiState: StateFlow<FeedsUIstate> = _feedsUiState


    /** 데이터가 비어있는지 여부 */
    val empty = MutableLiveData<Boolean>()

    /** 로그인 화면 열기 */
    //private val _openLogin = MutableLiveData<Event<Int>>()
    //val openLogin: LiveData<Event<Int>> = _openLogin

    /** 리뷰 화면 이동 */
    //private val _openAddReview = MutableLiveData<Event<Int>>()
    //val openAddReview: LiveData<Event<Int>> = _openAddReview

    /** 로그인 여부 */
    //val isLogin = feedRepository.isLogin

    fun clickAddReview() {
        viewModelScope.launch {
            _feedsUiState.update { it.copy(toastMsg = "clickAddReview") }
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
        }

        viewModelScope.launch {
            delay(1000)
            val response = feedRepository.loadFeed()
            var list = ArrayList<ItemFeedUIState>()

            if (response.status == 200) {
                response.data?.let {

                    Log.d(
                        TAG, GsonBuilder()
                            .setPrettyPrinting()
                            .create().toJson(it)
                    )
                    list = ArrayList(it.toFeedItemUiStateList())
                }
            }


            _feedsUiState.update {
                it.copy(
                    isRefresh = false,
                    isEmptyFeed = !it.isEmptyFeed,
                    feedItemUiState = list
                )
            }
        }

    }

    fun List<Feed>.toFeedItemUiStateList(): List<ItemFeedUIState> {
        return stream().map {
            it.toFeedItemUiState()
        }.toList()
    }

    fun Feed.toFeedItemUiState(): ItemFeedUIState {
        return ItemFeedUIState(
            itemId = review.reviewId.toLong(),
            itemFeedTopUiState = toItemFeedTopUiState(),
            itemFeedBottomUiState = toItemFeedBottonUiState(),
            reviewImages = ArrayList(pictures.stream().map {
                it.pictureUrl
            }.toList())
        )
    }

    fun Feed.toItemFeedTopUiState(): ItemFeedTopUIState {
        return ItemFeedTopUIState(
            reviewId = review.reviewId,
            name = author.userName,
            restaurantName = review.restaurant.restaurantName,
            rating = review.ratings,
            profilePictureUrl = author.profilePicUrl
        )
    }

    fun Feed.toItemFeedBottonUiState(): ItemFeedBottomUIState {
        return ItemFeedBottomUIState(
            reviewId = review.reviewId,
            likeAmount = likeAmount,
            commentAmount = commentAmount,
            author = author.userName,
            comment = review.contents,
            isLike = like.isLike,
            isFavorite = favorite.isFavority
        )
    }

    fun consumeGoLogin() {
        TODO("Not yet implemented")
    }

    init {
        //refreshFeed()
    }
}