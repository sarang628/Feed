package com.example.screen_feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.FeedItemUiState
import com.example.screen_feed.usecase.ItemTimeLineUseCase

class FeedsAdapter(
    private val navigation: FeedRvAdtNavigation? = null
) : RecyclerView.Adapter<ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        //return feeds[position].review_id.toLong()
        return 0
    }

    private var feeds = ArrayList<ItemTimeLineUseCase>()

    fun setFeeds(feeds: ArrayList<ItemTimeLineUseCase>) {
        this.feeds = feeds
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FeedsViewholder(
            ItemTimeLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as FeedsViewholder).fillHolder(
            useCase = feeds[position],
            clickName = { navigation?.goProfile(holder.itemView.context) }
        )
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}

private class FeedsViewholder(itemTimelineBinding: ItemTimeLineBinding) :
    ViewHolder(itemTimelineBinding.root) {
    private val binding = itemTimelineBinding

    fun fillHolder(
        useCase: ItemTimeLineUseCase,
        clickName: (() -> Unit)?
    ) {
        binding.useCase = useCase
    }
}