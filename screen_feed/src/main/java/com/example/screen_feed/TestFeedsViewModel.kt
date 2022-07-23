package com.example.screen_feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.ItemFeedBottomUsecase
import com.example.screen_feed.usecase.ItemFeedTopUseCase
import com.example.screen_feed.usecase.ItemTimeLineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestFeedsViewModel @Inject constructor(
    //private val feedRepository: FeedRepository
) : ViewModel() {

    private val _feedsUiState = MutableStateFlow(
        FeedsUIstate(
            isRefresh = false,
            isEmptyFeed = true
        )
    )

    val feedsUiState: StateFlow<FeedsUIstate> = _feedsUiState

    val useCase = ItemTimeLineUseCase(
        itemFeedTopUseCase = ItemFeedTopUseCase(
            name = "sryang",
            restaurantName = "mcdonalds",
            rating = 4.5f,
            profilePictureUrl = "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/",
            onMenuClickListener = { },
            onProfileImageClickListener = { },
            onNameClickListener = { },
            onRestaurantClickListener = { }
        ),
        itemFeedBottomUseCase = ItemFeedBottomUsecase(
            clickLikeListener = { },
            clickCommentListener = { },
            clickShareListener = { },
            clickFavoriteListener = { },
            likeAmount = 10,
            commentAmount = 20,
            author = "sryang",
            comment = "comment",
            isLike = true,
            isFavorite = true
        ),
        pageAdapter = FeedPagerAdapter().apply {
            setList(
                arrayListOf(
                    "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/",
                    "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/",
                    "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/"
                )
            )
        }
    )


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
            _feedsUiState.update {
                it.copy(
                    isRefresh = false,
                    isEmptyFeed = !it.isEmptyFeed,
                    feedItemUiState = arrayListOf(useCase, useCase, useCase)
                )
            }
        }

    }

    init {
        //refreshFeed()
    }
}