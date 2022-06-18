package com.example.screen_feed

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.sarang.base_feed.FeedVH
import com.example.torang_core.data.model.Feed
import com.example.torang_core.util.Logger

/**
 * [FeedVH]
 */
class FeedsRvAdt(
    private val lifecycleOwner: LifecycleOwner,
    private val timeLineViewModel: FeedsViewModel,
) : RecyclerView.Adapter<FeedVH>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return feeds[position].review_id.toLong()
    }

    private var feeds = ArrayList<Feed>()

    fun setFeeds(feedData: List<Feed>) {
        Logger.d("feeds size are ${feedData.size}")
        this.feeds = ArrayList(feedData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVH {
        Logger.d(viewType)
        return FeedVH.create(parent, timeLineViewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: FeedVH, position: Int) {
        Logger.d("$position")
        holder.setFeed(feeds[position])
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}