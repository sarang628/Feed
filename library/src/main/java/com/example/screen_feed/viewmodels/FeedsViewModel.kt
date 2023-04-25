package com.example.screen_feed.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.uistate.FeedsUIstate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel
@Inject
constructor(
//    private val feedRepository: FeedRepository
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
            try {
//                feedRepository.deleteFeed(reviewId)

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
            try {
                /*feedRepository.loadFeed().apply {
                    Log.d(TAG, GsonBuilder().setPrettyPrinting().create().toJson(this))
                    _feedsUiState.update {
                        it.copy(
                            isRefresh = false,
                            isEmptyFeed = this.isEmpty(),
                            feedItemUiState = ArrayList(this.toFeedItemUiStateList())
                        )
                    }
                }*/
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

    /*fun List<Feed>.toFeedItemUiStateList(): List<ItemFeedUIState> {
        return stream().map {
            it.toFeedItemUiState()
        }.toList()
    }*/

    /*fun Feed.toFeedItemUiState(): ItemFeedUIState {
        return ItemFeedUIState(
            itemId = review.reviewId.toLong(),
            itemFeedTopUiState = toItemFeedTopUiState(),
            itemFeedBottomUiState = toItemFeedBottonUiState(),
            reviewImages = ArrayList(pictures.stream().map {
                it.pictureUrl
            }.toList())
        )
    }*/

    /*fun Feed.toItemFeedTopUiState(): ItemFeedTopUIState {
        return ItemFeedTopUIState(
            reviewId = review.reviewId,
            name = author.userName,
            restaurantName = review.restaurant.restaurantName,
            rating = review.ratings,
            profilePictureUrl = author.profilePicUrl
        )
    }*/

    /*fun Feed.toItemFeedBottonUiState(): ItemFeedBottomUIState {
        return ItemFeedBottomUIState(
            reviewId = review.reviewId,
            likeAmount = likeAmount,
            commentAmount = commentAmount,
            author = author.userName,
            comment = review.contents,
            isLike = like.isLike,
            isFavorite = favorite.isFavority
        )
    }*/

    fun clickLike(it: Int) {
        Log.e(TAG, "Not yet implemented")
    }

    fun clickFavorite(it: Int) {
        Log.e(TAG, "Not yet implemented")
    }
}