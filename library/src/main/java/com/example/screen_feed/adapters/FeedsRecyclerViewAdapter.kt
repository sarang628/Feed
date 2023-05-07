package com.example.screen_feed.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.screen_feed.databinding.ItemFeedBinding
import com.example.screen_feed.uistate.FeedUiState
import com.google.gson.GsonBuilder

class FeedsRecyclerViewAdapter(
//    val lifecycleOwner: LifecycleOwner
    ) :
    RecyclerView.Adapter<ViewHolder>() {

    private var feeds = ArrayList<FeedUiState>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        if(feeds[position].reviewId == null)
            return 0L

        return feeds[position].reviewId!!.toLong()
    }

    fun setFeeds(feeds: ArrayList<FeedUiState>) {
        this.feeds = feeds
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FeedsViewholder(
            binding = ItemFeedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("sryang123", GsonBuilder().setPrettyPrinting().create().toJson(feeds[position]))
        (holder as FeedsViewholder).fillHolder(
            useCase = feeds[position]
        )
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}