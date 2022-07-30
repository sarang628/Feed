package com.posco.feedscreentestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.screen_feed.databinding.ItemFeedBottomBinding
import com.example.screen_feed.usecase.ItemFeedBottomUsecase

class ItemFeedBottomTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ItemFeedBottomBinding>(this, com.example.screen_feed.R.layout.item_feed_bottom)
        /*binding.useCase = ItemFeedBottomUsecase(
            clickLikeListener = {},
            clickCommentListener = {},
            clickShareListener = {},
            clickFavoriteListener = {},
            likeAmount = 10,
            commentAmount = 20,
            author = "sryang",
            comment = "comment",
            isLike = true,
            isFavorite = true
        )*/
    }
}