package com.example.screen_feed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.databinding.ItemTimeLineBindingImpl

/**
 * [FeedVH]
 */
class FeedsRvAdt(
    private val lifecycleOwner: LifecycleOwner,
    private val navigation : FeedRvAdtNavigation? = null
    /*private val timeLineViewModel: FeedsViewModel? = null,
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
    private val getFavorite: ((Int) -> LiveData<Favorite>)? = null*/
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
        //Logger.d("feeds size are ${feedData.size}")
        //this.feeds = ArrayList(feedData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Logger.d(viewType)
        /*return FeedVH.create(
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
        )*/
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_time_line, parent, false)
        ) {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Logger.d("$position")
        //holder.setFeed(feeds[position])
        val binding = ItemTimeLineBinding.bind(holder.itemView)
        binding.itemFeedTop.textView22.text = feeds[position].name

        binding.itemFeedTop.textView22.setOnClickListener{
            navigation?.goProfile(holder.itemView.context)
        }
    }

    override fun getItemCount(): Int {
        //return feeds.size
        return feeds.size
    }
}