package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.screen_feed.databinding.ItemFeedBottomBinding
import com.example.screen_feed.usecase.ItemFeedBottomUIState
import com.example.screen_feed.usecase.ItemFeedBottomUsecase

class ItemFeedBottomTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ItemFeedBottomBinding>(this, com.example.screen_feed.R.layout.item_feed_bottom)
        binding.useCase = ItemFeedBottomUsecase(
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
        )
    }
}