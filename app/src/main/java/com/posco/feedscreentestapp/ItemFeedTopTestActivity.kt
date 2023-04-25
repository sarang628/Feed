package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.screen_feed.databinding.ItemFeedTopBinding
import com.example.screen_feed.usecase.ItemFeedTopUIState
import com.example.screen_feed.usecase.ItemFeedTopUseCase
import com.google.android.material.snackbar.Snackbar


class ItemFeedTopTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ItemFeedTopBinding>(
            this,
            com.example.screen_feed.R.layout.item_feed_top
        )
        binding.useCase = ItemFeedTopUseCase(
            data = ItemFeedTopUIState(
                reviewId = 0,
                name = "1",
                restaurantName = "2",
                rating = 3.0f,
                profilePictureUrl = "4"
            ),
            onMenuClickListener = { Snackbar.make(this, binding.root, "clickMenu", Snackbar.LENGTH_SHORT).show() },
            onProfileImageClickListener = { Snackbar.make(this, binding.root, "profileClick", Snackbar.LENGTH_SHORT).show() },
            onNameClickListener = { Snackbar.make(this, binding.root, "nameClick", Snackbar.LENGTH_SHORT).show() },
            onRestaurantClickListener = { Snackbar.make(this, binding.root, "restaurantClick", Snackbar.LENGTH_SHORT).show() }
        )
    }
}