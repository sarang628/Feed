package com.example.screen_feed.uistate

import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.adapters.FeedPagerAdapter


data class ItemFeedUIState(
    val itemId: Long,
    val itemFeedTopUiState: ItemFeedTopUIState,
    val itemFeedBottomUiState: ItemFeedBottomUIState,
    val reviewImages: ArrayList<String>,
    val visibleReviewImage: Boolean = false,
    val pageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    val imageClickListener: (Int) -> Unit
)

val ItemFeedUIState.pictureVisible: Boolean get() = reviewImages.isEmpty()

fun ItemFeedUIState.getAdapter(): FeedPagerAdapter {
    (pageAdapter as FeedPagerAdapter).setOnImageClickListener(imageClickListener)
    return pageAdapter
}