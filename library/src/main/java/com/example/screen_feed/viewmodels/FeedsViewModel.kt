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

    // 피드
    fun deleteFeed(reviewId: Int) {
        viewModelScope.launch {
            _feedsUiState.update {
                it.copy(
                    isProgess = true
                )
            }
            delay(1000)
            try {
                feedRepository.deleteFeed(reviewId)

                _feedsUiState.update {
                    it.copy(
                        isProgess = false
                    )
                }
            } catch (e: Exception) {
                _feedsUiState.update {
                    it.copy(
                        isProgess = false,
                        errorMsg = e.toString()
                    )
                }
            }
        }
    }

    fun reload() {
        viewModelScope.launch {
            _feedsUiState.update {
                it.copy(isRefresh = true)
            }
        }

        viewModelScope.launch {
            delay(1000)
            try {
                feedRepository.loadFeed().data!!.apply {
                    Log.d(TAG, GsonBuilder().setPrettyPrinting().create().toJson(this))
                    _feedsUiState.update {
                        it.copy(
                            isRefresh = false,
                            isEmptyFeed = !it.isEmptyFeed,
                            feedItemUiState = ArrayList(this.toFeedItemUiStateList())
                        )
                    }
                }
            } catch (e: Exception) {
                _feedsUiState.update {
                    it.copy(
                        isRefresh = false,
                        errorMsg = e.toString()
                    )
                }
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
}