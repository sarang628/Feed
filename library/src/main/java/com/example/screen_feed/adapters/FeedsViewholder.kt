package com.example.screen_feed.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.ItemFeedUIState
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
        useCase: ItemFeedUIState
    ) {
        binding.useCase = useCase
        binding.viewpager.adapter = useCase.getAdapter()//FIXME::왜 바인딩이 안되는가?
        TabLayoutMediator(binding.tlIndicator, binding.viewpager) { tab, position ->
        }.attach()
    }
}