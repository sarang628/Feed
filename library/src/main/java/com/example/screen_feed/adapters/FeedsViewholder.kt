package com.example.screen_feed.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.FeedUiState
import com.example.screen_feed.uistate.getAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FeedsViewholder(
//    lifecycleOwner: LifecycleOwner,
    binding: ItemTimeLineBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    private val binding = binding

    init {
//        binding.lifecycleOwner = lifecycleOwner
    }

    fun fillHolder(
        useCase: FeedUiState
    ) {
        binding.useCase = useCase
        useCase.getAdapter()?.let {
            binding.viewpager.adapter = it//FIXME::왜 바인딩이 안되는가?
            TabLayoutMediator(binding.tlIndicator, binding.viewpager) { tab, position -> }.attach()
        }
    }
}