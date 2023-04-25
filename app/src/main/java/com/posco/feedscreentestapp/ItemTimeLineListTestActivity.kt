package com.posco.feedscreentestapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.screen_feed.adapters.FeedPagerAdapter
import com.example.screen_feed.adapters.FeedsRecyclerViewAdapter
import com.example.screen_feed.usecase.*
import com.google.android.material.snackbar.Snackbar
import com.posco.feedscreentestapp.databinding.ActivityItemTimeLineListTestBinding

class ItemTimeLineListTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityItemTimeLineListTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val useCase = ItemFeedUseCase(
            itemId = 0,
            itemFeedTopUseCase = ItemFeedTopUseCase(
                data = ItemFeedTopUIState(
                    reviewId = 0,
                    name = "1",
                    restaurantName = "2",
                    rating = 3.0f,
                    profilePictureUrl = "http://sarang628.iptime.org:88/1.png"
                ),
                onMenuClickListener = { Snackbar.make(this, binding.root, "clickMenu", Snackbar.LENGTH_SHORT).show() },
                onProfileImageClickListener = { Snackbar.make(this, binding.root, "profileClick", Snackbar.LENGTH_SHORT).show() },
                onNameClickListener = { Snackbar.make(this, binding.root, "nameClick", Snackbar.LENGTH_SHORT).show() },
                onRestaurantClickListener = { Snackbar.make(this, binding.root, "restaurantClick", Snackbar.LENGTH_SHORT).show() }
            ),
            itemFeedBottomUseCase = ItemFeedBottomUsecase(
                data = ItemFeedBottomUIState(
                    reviewId = 0,
                    likeAmount = 1,
                    commentAmount =2,
                    author = "3",
                    comment = "4",
                    isLike = false,
                    isFavorite = true
                ),
                onLikeClickListener = {},
                onCommentClickListener = {},
                onShareClickListener = {},
                onClickFavoriteListener = {},
                visibleLike = true,
                visibleComment = true
            ),
            pageAdapter = FeedPagerAdapter().apply {
                setList(
                    arrayListOf(
                        "http://sarang628.iptime.org:88/1.png",
                        "http://sarang628.iptime.org:88/1.png",
                        "http://sarang628.iptime.org:88/1.png"
                    )
                )
            },
            visibleReviewImage = true
        )

        binding.rv.apply {
            adapter = FeedsRecyclerViewAdapter().apply {
                setFeeds(
                    arrayListOf(useCase,useCase,useCase)
                )
            }
        }

    }

    fun snackBar(view: View, text: String) {
        Snackbar.make(
            this, view, text, Snackbar.LENGTH_SHORT
        ).show()
    }
}