package com.example.screen_feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.adapters.FeedPageHolder
import com.example.screen_feed.databinding.ItemFeedPageBinding
import com.sarang.torangimageloader.ImageLoadBindingAdapter

class FeedPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemFeedPageBinding =
            ItemFeedPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPageHolder(itemFeedPageBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FeedPageHolder).binding.imgUrl =
            "http://vrscoo.com:91/review_images/${list[position]}"

    }

    fun setList(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}