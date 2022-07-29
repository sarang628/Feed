package com.example.screen_feed.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.usecase.ItemFeedUseCase

class FeedsViewholder(itemTimelineBinding: ItemTimeLineBinding) :
    RecyclerView.ViewHolder(itemTimelineBinding.root) {
    private val binding = itemTimelineBinding

    fun fillHolder(
        useCase: ItemFeedUseCase) {
        binding.useCase = useCase
    }
}