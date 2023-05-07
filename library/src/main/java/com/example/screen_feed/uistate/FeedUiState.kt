package com.example.screen_feed.uistate

import androidx.recyclerview.widget.RecyclerView

/*피드 UIState*/
data class FeedUiState(
    val reviewId: Int? = 0,
    val itemFeedTopUiState: FeedTopUIState? = null,
    val itemFeedBottomUiState: FeedBottomUIState? = null,
    val reviewImages: List<String>? = ArrayList(),
    val visibleReviewImage: Boolean = false,
    val pageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null,
    val imageClickListener: ((Int) -> Unit)? = null
)