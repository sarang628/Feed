package com.example.screen_feed

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.torang_core.data.FeedUiState
import com.example.torang_core.data.model.*
import com.sarang.base_feed.FeedVH
import com.example.torang_core.util.Logger

/**
 * [FeedVH]
 */
class FeedsRvAdt(
    private val clickMenu: ((Feed) -> Unit)? = null,
    private val clickProfile: ((Int) -> Unit)? = null,
    private val clickRestaurant: ((Int) -> Unit)? = null,
    private val clickLike: ((View, Int) -> Unit)? = null,
    private val clickComment: ((Int) -> Unit)? = null,
    private val clickShare: ((Int) -> Unit)? = null,
    private val clickFavorite: ((View, Int) -> Unit)? = null,
    private val clickPicture: ((ReviewImage) -> Unit)? = null
) : RecyclerView.Adapter<FeedVH>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return uiStates[position].reviewId.toLong()
    }

    private var uiStates: ArrayList<FeedUiState> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVH {
        Logger.d(viewType)
        return FeedVH.create(
            parent,
            clickMenu,
            clickProfile,
            clickRestaurant,
            clickLike,
            clickComment,
            clickShare,
            clickFavorite,
            clickPicture
        )
    }

    override fun onBindViewHolder(holder: FeedVH, position: Int) {
        val feed = uiStates[position]

        holder.setFeed(
            reviewId = feed.reviewId,
            profilePicUrl = feed.profileImageUrl,
            userId = feed.userId,
            userName = feed.userName,
            rating = feed.rating,
            restaurantName = feed.restaurantName,
            restaurantId = feed.restaurantId,
            likeAmount = feed.likeAmount,
            contents = feed.contents,
            commentAnount = feed.commentAmount,
            isLike = feed.isLike,
            isFavorite = feed.isFavorite,
            reviewImages = feed.reviewImages
        )
    }

    override fun getItemCount(): Int {
        return uiStates.size
    }

    fun setUiState(it: List<FeedUiState>) {
        for (i in it.indices) {
            if (i < uiStates.size && !uiStates[i].equals(it[i])) {
                Logger.d("position changed! $i")
                uiStates[i] = it[i]
            } else if (i > uiStates.size) {
                Logger.d("list add! $i")
                uiStates.add(it[i])
            }
        }

        if (uiStates.size > it.size) {
            for (i in it.size - 1 until uiStates.size - 1) {
                Logger.d("list remove! $i")
                uiStates.removeAt(it.size)
            }
        }
        notifyDataSetChanged()
    }
}