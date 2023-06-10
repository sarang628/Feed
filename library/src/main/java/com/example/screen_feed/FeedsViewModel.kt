package com.example.screen_feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.data.Feed
import com.sarang.base_feed.uistate.FeedsScreenUiState
import com.sarang.base_feed.uistate.testEmptyFeedOff
import com.sarang.base_feed.uistate.testEmptyFeedOn
import com.sarang.base_feed.uistate.testFailedConnectionOff
import com.sarang.base_feed.uistate.testFailedConnectionOn
import com.sarang.base_feed.uistate.testProgressOff
import com.sarang.base_feed.uistate.testProgressOn
import com.sarang.base_feed.uistate.testRefreshingOff
import com.sarang.base_feed.uistate.testRefreshingOn
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.entity.FeedEntity
import com.sryang.torang_repository.data.entity.FeedEntity1
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList

class FeedsViewModel @Inject constructor(
    val feedDao: FeedDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FeedsScreenUiState()
    )
    val uiState: StateFlow<FeedsScreenUiState> = _uiState

    init {
        testShowList()
//        test()

        viewModelScope.launch {
            feedDao.getAllFeed1().collect {
                _uiState.emit(
                    _uiState.value.copy(
                        feeds = ArrayList<Feed>().apply {
                            addAll(it.stream().map { it.toFeed() }.toList())
                        }
                    )
                )
            }
        }


    }

    fun FeedEntity.toFeed(): Feed {
        return Feed(
            name = userName,
            contents = contents,
            rating = rating,
            profilePictureUrl = profilePicUrl
        )
    }

    fun test() {
        val delayCount = 1000L
        viewModelScope.launch {
            while (true) {
//             스와이프 리프레시 테스트
                _uiState.emit(testRefreshingOn()); delay(delayCount)
                _uiState.emit(testRefreshingOff()); delay(delayCount)
//             프로그레스 테스트
                _uiState.emit(testProgressOn()); delay(delayCount)
                _uiState.emit(testProgressOff()); delay(delayCount)
//             비어있는 피드 테스트
                _uiState.emit(testEmptyFeedOn()); delay(delayCount)
                _uiState.emit(testEmptyFeedOff()); delay(delayCount)
//             네트워크 연결 실패 테스트
                _uiState.emit(testFailedConnectionOn()); delay(delayCount)
                _uiState.emit(testFailedConnectionOff()); delay(delayCount)
//             피드 테스트
//                _uiState.emit(getTestFeedList(context)); delay(30000)
            }
        }
    }

    fun testShowList() {
//        viewModelScope.launch {
//            _uiState.emit(getTestFeedList(context))
//        }
    }

    fun testShowEmpty() {
        viewModelScope.launch {
            _uiState.emit(testEmptyFeedOn())
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = true,
                )
            )
            delay(2000)
            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = false
                )
            )
        }
    }

    fun clickName() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickName"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickRestaurant() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickRestaurant"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }

    }

    fun clickMenu() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickMenu"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }

    }

    fun clickImage() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickImage"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }

    }

    fun clickProfile() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickProfile"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickAddReview() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickAddReview"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickShare() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickShare"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickComment() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickComment"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickLike(id: Int) {
        Log.d("FeedsViewModel", id.toString())
        viewModelScope.launch {
            _uiState.value.feeds?.let {
                val list = it.stream().map { feed ->
                    if (feed.reviewId == id)
                        feed.copy(isLike = !feed.isLike!!)
                    else
                        feed
                }.toList()

                _uiState.emit(
                    _uiState.value.copy(
                        feeds = ArrayList(list)
                    )
                )
            }
        }
    }

    fun clickFavorite(id: Int) {
        viewModelScope.launch {
            _uiState.value.feeds?.let {
                val list = it.stream().map { feed ->
                    if (feed.reviewId == id)
                        feed.copy(isFavorite = !feed.isFavorite!!)
                    else
                        feed
                }.toList()

                _uiState.emit(
                    _uiState.value.copy(
                        feeds = ArrayList(list)
                    )
                )
            }
        }
    }

}

fun FeedEntity1.toFeed(): Feed {
    return Feed(
        name = this.user.userName,
        profilePictureUrl = this.user.profilePicUrl,
        reviewImages = this.reviewImages.stream().map { it.picture_url }.toList(),
        contents = this.user.contents,
        restaurantName = this.user.restaurantName
    )
}