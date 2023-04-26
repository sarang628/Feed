package com.example.screen_feed.uistate

import androidx.recyclerview.widget.RecyclerView


data class ItemFeedUIState(
    val itemId: Long,
    val itemFeedTopUiState: ItemFeedTopUIState,
    val itemFeedBottomUiState: ItemFeedBottomUIState,
    val reviewImages: ArrayList<String>,
    val visibleReviewImage: Boolean = false,
    val pageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
)
val ItemFeedUIState.pictureVisible: Boolean get() = reviewImages.isEmpty()