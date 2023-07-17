package com.example.screen_feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.base_feed.data.Feed
import com.sarang.base_feed.uistate.FeedsScreenUiState
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.entity.FeedEntity
import com.sryang.torang_repository.data.entity.FeedEntity1
import com.sryang.torang_repository.data.remote.response.RemoteFeed
import com.sryang.torang_repository.services.FeedServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList

class FeedsViewModel @Inject constructor(
    private val feedDao: FeedDao, private val feedServices: FeedServices
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FeedsScreenUiState()
    )
    val uiState: StateFlow<FeedsScreenUiState> = _uiState

    init {
        /*viewModelScope.launch {
            feedDao.getAllFeed1().collect {
                _uiState.emit(
                    _uiState.value.copy(
                        feeds = ArrayList<Feed>().apply {
                            addAll(it.stream().map { it.toFeed() }.toList())
                        }
                    )
                )
            }
        }*/

        refreshFeed()
    }

    fun RemoteFeed.toFeed(): Feed {
        return Feed(
            reviewId = reviewId,
            reviewImages = pictures.stream().map {
                "http://sarang628.iptime.org:89/review_images/" + it.picture_url
            }.toList(),
            name = user?.userName,
            restaurantName = restaurant?.restaurantName,
        )
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

    fun refreshFeed() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = true
                )
            )

            val result = feedServices.getFeeds(HashMap())
            _uiState.emit(
                _uiState.value.copy(
                    feeds = ArrayList<Feed>().apply {
                        addAll(result.stream().map {
                            Log.d("sryang123", it.toString())
                            it.toFeed()
                        }.toList())
                    },
                    isRefreshing = false
                )
            )
        }
    }

    fun onBottom() {
        Log.d("sryang123", "onBottom!")
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

fun FeedEntity.toFeed(): Feed {
    return Feed(
        name = userName,
        contents = contents,
        rating = rating,
        profilePictureUrl = profilePicUrl
    )
}