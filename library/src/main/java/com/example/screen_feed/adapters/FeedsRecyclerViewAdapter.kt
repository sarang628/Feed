package com.example.screen_feed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.screen_feed.ItemFeedBottom
import com.example.screen_feed.ItemFeedMid
import com.example.screen_feed.ItemFeedTop
import com.example.screen_feed.databinding.ItemFeedBinding
import com.example.screen_feed.uistate.FeedUiState
import com.example.screen_feed.uistate.getAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FeedsRecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var feedsUiState = ArrayList<FeedUiState>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        if (feedsUiState[position].reviewId == null)
            return 0L

        return feedsUiState[position].reviewId!!.toLong()
    }

    fun setFeeds(feeds: ArrayList<FeedUiState>) {
        this.feedsUiState = feeds
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FeedsViewHolder(
            binding = ItemFeedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as FeedsViewHolder).fillHolder(uiState = feedsUiState[position])
    }

    override fun getItemCount(): Int {
        return feedsUiState.size
    }
}


class FeedsViewHolder(private val binding: ItemFeedBinding) :
    ViewHolder(binding.root) {
    fun fillHolder(uiState: FeedUiState) {
        binding.cvFeedTop.setContent {
            Column() {
                ItemFeedTop(uiState.itemFeedTopUiState)
                Spacer(modifier = Modifier.height(4.dp))
                ItemFeedMid(uiState.reviewImages)
                ItemFeedBottom(uiState.itemFeedBottomUiState)
            }
        }
    }
}