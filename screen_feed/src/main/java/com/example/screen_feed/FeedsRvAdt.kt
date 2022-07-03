package com.example.screen_feed

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.torang_core.data.FeedUiState
import com.example.torang_core.data.model.*
import com.sarang.base_feed.FeedVH
import com.example.torang_core.util.Logger

/**
 * [FeedVH]
 */
class FeedsRvAdt(
    private val lifecycleOwner: LifecycleOwner,
    private val timeLineViewModel: FeedsViewModel? = null,
    private val clickMenu: ((Feed) -> Unit)? = null,
    private val clickProfile: ((Int) -> Unit)? = null,
    private val clickRestaurant: ((Int) -> Unit)? = null,
    private val clickLike: ((View, Int) -> Unit)? = null,
    private val clickComment: ((Int) -> Unit)? = null,
    private val clickShare: ((Int) -> Unit)? = null,
    private val clickFavorite: ((View, Int) -> Unit)? = null,
    private val clickPicture: ((ReviewImage) -> Unit)? = null,
    private val getReviewImage: ((Int) -> LiveData<List<ReviewImage>>)? = null,
    private val getLike: ((Int) -> LiveData<Like>)? = null,
    private val getFavorite: ((Int) -> LiveData<Favorite>)? = null
) : RecyclerView.Adapter<FeedVH>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return feeds[position].review_id.toLong()
    }

    private var feeds = ArrayList<Feed>()

    private var uistates: ArrayList<FeedUiState> = ArrayList()

    fun setFeeds(feedData: List<Feed>) {
        Logger.d("feeds size are ${feedData.size}")
        this.feeds = ArrayList(feedData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVH {
        Logger.d(viewType)
        return FeedVH.create(
            parent,
            lifecycleOwner,
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
        val feed = uistates[position]

        getLike?.invoke(feed.reviewId)?.observe(lifecycleOwner) {
            holder.setLike(it != null)
        }

        getFavorite?.invoke(feed.reviewId)?.observe(lifecycleOwner) {
            holder.setFavorite(it != null)
        }

        getReviewImage?.invoke(feed.reviewId)?.observe(lifecycleOwner) {
            holder.setReviewImages(it)
        }

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
            commentAnount = feed.commentAmount
        )
    }

    override fun getItemCount(): Int {
        return uistates.size
    }

    fun setUiState(it: List<FeedUiState>) {
        for (i in it.indices) {
            if (i < uistates.size && !uistates[i].equals(it[i])) {
                Logger.d("position changed! $i")
                uistates[i] = it[i]
            } else if (i > uistates.size) {
                Logger.d("list add! $i")
                uistates.add(it[i])
            }
        }

        if (uistates.size > it.size) {
            for (i in it.size - 1 until uistates.size - 1) {
                Logger.d("list remove! $i")
                uistates.removeAt(it.size)
            }
        }
    }
}