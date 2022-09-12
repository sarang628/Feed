package com.example.screen_feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.adapters.FeedPageHolder
import com.sarang.torangimageloader.ImageLoadBindingAdapter

class FeedPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FeedPageHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed_page, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ImageLoadBindingAdapter.loadImage(
            holder.itemView.findViewById(R.id.iv_page),
            "http://vrscoo.com:91/review_images/${list[position]}"
        )

    }

    fun setList(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}