package com.example.screen_feed

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.torang_core.data.model.Favorite
import com.sarang.base_feed.FeedVH
import com.example.torang_core.data.model.Feed
import com.example.torang_core.data.model.Like
import com.example.torang_core.data.model.ReviewImage
import com.example.torang_core.util.Logger
import com.sarang.base_feed.CustomClick
import com.sarang.base_feed.FeedVH1

/**
 * [FeedVH]
 */
class FeedsRvAdt1(
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
) : RecyclerView.Adapter<FeedVH1>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVH1 {
        Logger.d(viewType)
        return FeedVH1.create(
            parent,
            lifecycleOwner,
            clickMenu,
            clickProfile,
            clickRestaurant,
            clickLike,
            clickComment,
            clickShare,
            clickFavorite,
            clickPicture,
            getReviewImage,
            getLike,
            getFavorite
        )
    }

    override fun onBindViewHolder(holder: FeedVH1, position: Int) {
        Logger.d("$position")
        holder.setFeed(
            feed = feeds[position],
            reviewId = feeds[position].review_id,
            userId = feeds[position].userId,
            restaurantId = feeds[position].restaurantId,
            profilePicUrl = feeds[position].profile_pic_url,
            userName = feeds[position].userName,
            restaurantName = feeds[position].restaurantName,
            rating = feeds[position].rating,
            menuClickListener = CustomClick {
                clickMenu?.invoke(feeds[position])
            },
            profileClickListener = CustomClick {
                clickProfile?.invoke(it)
            },
            clickRestaurantListener = CustomClick {
                clickRestaurant?.invoke(it)
            }
        )
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}