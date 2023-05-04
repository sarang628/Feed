package com.example.screen_feed.uistate

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.adapters.FeedPagerAdapter
import com.example.screen_feed.data.Feed
import com.google.android.material.snackbar.Snackbar

/*피드 UIState*/
data class FeedUiState(
    val itemId: Long = 0,
    val itemFeedTopUiState: FeedTopUIState? = null,
    val itemFeedBottomUiState: FeedBottomUIState? = null,
    val reviewImages: ArrayList<String> = ArrayList(),
    val visibleReviewImage: Boolean = false,
    val pageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null,
    val imageClickListener: ((Int) -> Unit)? = null
)

fun testItemFeedUiState(context: Context, view: View) = FeedUiState(
    itemId = 0,
    itemFeedTopUiState = testItemFeedTopUIState(context, view),
    itemFeedBottomUiState = testItemFeedBottomUiState(context, view),
    pageAdapter = FeedPagerAdapter().apply {
        setList(
            arrayListOf(
                "http://sarang628.iptime.org:88/1.png",
                "http://sarang628.iptime.org:88/1.png",
                "http://sarang628.iptime.org:88/1.png"
            )
        )
    },
    visibleReviewImage = true,
    reviewImages = ArrayList(),
    imageClickListener = {
        Snackbar.make(context, view, "imageClick " + it, Snackbar.LENGTH_SHORT).show()
    }
)

fun Feed.testItemFeedUiState(context: Context, view: View) = FeedUiState(
    itemId = 0,
    itemFeedTopUiState = testItemFeedTopUIState(context, view),
    itemFeedBottomUiState = testItemFeedBottomUiState(context, view),
    pageAdapter = FeedPagerAdapter().apply {
        setList(
            arrayListOf(
                "http://sarang628.iptime.org:88/1.png",
                "http://sarang628.iptime.org:88/1.png",
                "http://sarang628.iptime.org:88/1.png"
            )
        )
    },
    visibleReviewImage = true,
    reviewImages = ArrayList(),
    imageClickListener = {
        Snackbar.make(context, view, "imageClick " + it, Snackbar.LENGTH_SHORT).show()
    }
)