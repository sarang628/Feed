package com.example.screen_feed

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
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
        Logger.d("$position")
        val feed = feeds[position]

        getLike?.invoke(feed.review_id)?.observe(lifecycleOwner){
            holder.setLike(it != null)
        }

        getFavorite?.invoke(feed.review_id)?.observe(lifecycleOwner){
            holder.setFavorite(it != null)
        }

        getReviewImage?.invoke(feed.review_id)?.observe(lifecycleOwner){
            holder.setReviewImages(it)
        }

        holder.setFeed(
            reviewId = feed.review_id,
            profilePicUrl = feed.profile_pic_url!!,
            userId =  feed.userId,
            userName = feed.userName(),
            rating = feed.rating!!,
            restaurantName = feed.restaurantName(),
            restaurantId = feed.restaurantId!!,
            likeAmount = feed.like_amount!!,
            contents = feed.contents!!,
            commentAnount = feed.comment_amount!!,
            feed = feed
        )
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}