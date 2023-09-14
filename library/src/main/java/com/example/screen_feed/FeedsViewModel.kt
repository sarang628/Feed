package com.example.screen_feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.base_feed.data.Feed
import com.sarang.base_feed.uistate.FeedsScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList

class FeedsViewModel @Inject constructor(
    private val feedService: FeedService
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FeedsScreenUiState()
    )
    val uiState: StateFlow<FeedsScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            val result = feedService.getFeeds(HashMap())
            _uiState.emit(
                _uiState.value.copy(
                    feeds = ArrayList(result)
                )
            )
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

    fun refreshFeed() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = true
                )
            )

            val result = feedService.getFeeds(HashMap())
            _uiState.emit(
                _uiState.value.copy(
                    feeds = ArrayList<Feed>().apply {
                        addAll(result)
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

/*fun FeedEntity1.toFeed(): Feed {
    return Feed(
        name = this.user.userName,
        profilePictureUrl = this.user.profilePicUrl,
        reviewImages = this.reviewImages.stream().map { it.picture_url }.toList(),
        contents = this.user.contents,
        restaurantName = this.user.restaurantName
    )
}*/

/*
fun FeedEntity.toFeed(): Feed {
    return Feed(
        name = userName,
        contents = contents,
        rating = rating,
        profilePictureUrl = profilePicUrl
    )
}
*/
