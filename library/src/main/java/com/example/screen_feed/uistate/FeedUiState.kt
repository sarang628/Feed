package com.example.screen_feed.uistate

import com.example.screen_feed.data.Feed

/*피드 UIState*/
data class FeedUiState(
    val reviewId: Int? = 0,
    val itemFeedTopUiState: FeedTopUIState? = null,
    val itemFeedBottomUiState: FeedBottomUIState? = null,
    val reviewImages: List<String>? = ArrayList(),
    val visibleReviewImage: Boolean = false,
    val imageClickListener: ((Int) -> Unit)? = null,

    )

fun Feed.FeedUiState(): FeedUiState {
    return FeedUiState(
        reviewId = this.reviewId,
        itemFeedTopUiState = this.FeedTopUiState(),
        itemFeedBottomUiState = this.FeedBottomUIState(),
        reviewImages = this.reviewImages,
        visibleReviewImage = true
    )
}