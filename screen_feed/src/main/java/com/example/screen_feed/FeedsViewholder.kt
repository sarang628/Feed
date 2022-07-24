package com.example.screen_feed

import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.usecase.ItemTimeLineUseCase

class FeedsViewholder(itemTimelineBinding: ItemTimeLineBinding) :
    RecyclerView.ViewHolder(itemTimelineBinding.root) {
    private val binding = itemTimelineBinding

    fun fillHolder(
        useCase: ItemTimeLineUseCase,
        clickName: (() -> Unit)?
    ) {
        binding.useCase = useCase
    }
}