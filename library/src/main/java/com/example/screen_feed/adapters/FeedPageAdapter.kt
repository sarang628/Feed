package com.example.screen_feed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemFeedPageBinding

class FeedPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list : List<String> = ArrayList()

    private var imageClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemFeedPageBinding =
            ItemFeedPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPageHolder(itemFeedPageBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FeedPageHolder).binding.imgUrl = list[position]
        holder.binding.ivPage.setOnClickListener {
            this.imageClickListener?.invoke(position)
        }
    }

    fun setList(list: List<String>) {
        this.list = list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnImageClickListener(listener: (Int) -> Unit) {
        this.imageClickListener = listener
    }

}

class FeedPageHolder(val binding: ItemFeedPageBinding) : RecyclerView.ViewHolder(binding.root)