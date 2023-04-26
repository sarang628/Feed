package com.example.screen_feed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.ItemFeedUIState

class FeedsRecyclerViewAdapter(
//    val lifecycleOwner: LifecycleOwner
    ) :
    RecyclerView.Adapter<ViewHolder>() {

    private var feeds = ArrayList<ItemFeedUIState>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return feeds[position].itemId
    }

    fun setFeeds(feeds: ArrayList<ItemFeedUIState>) {
        this.feeds = feeds
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FeedsViewholder(
//            lifecycleOwner = lifecycleOwner,
            binding = ItemTimeLineBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as FeedsViewholder).fillHolder(
            useCase = feeds[position]
        )
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}