package com.example.screen_feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemTimeLineBinding

class FeedsAdapter(
    private val navigation: FeedRvAdtNavigation? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        //return feeds[position].review_id.toLong()
        return 0
    }

    private var feeds = ArrayList<FeedItemUiState>().apply {
        add(FeedItemUiState("a"))
        add(FeedItemUiState("b"))
        add(FeedItemUiState("c"))
        add(FeedItemUiState("d"))
        add(FeedItemUiState("e"))
        add(FeedItemUiState("f"))
    }

    fun setFeeds(/*feedData: List<Feed>*/) {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_time_line, parent, false)
        ) {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = ItemTimeLineBinding.bind(holder.itemView)
        binding.itemFeedTop.textView22.text = feeds[position].name

        binding.itemFeedTop.textView22.setOnClickListener {
            navigation?.goProfile(holder.itemView.context)
        }
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}