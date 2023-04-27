package com.example.screen_feed.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemFeedPageBinding

class FeedPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<String>()

    var imageClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemFeedPageBinding =
            ItemFeedPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPageHolder(itemFeedPageBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FeedPageHolder).binding.imgUrl = list[position]
        holder.binding.ivPage.setOnClickListener {
            this.imageClickListener?.let {
                Log.d("TEST", "click" + position)
                it.invoke(position)
            }
        }
    }

    fun setList(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnImageClickListener(listener: (Int) -> Unit) {
        this.imageClickListener = listener
    }

}