package com.example.screen_feed.usecase

import androidx.recyclerview.widget.RecyclerView

data class ItemTimeLineUseCase(
    val itemFeedTopUseCase: ItemFeedTopUseCase,
    val itemFeedBottomUseCase: ItemFeedBottomUsecase,
    val pageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>
)
