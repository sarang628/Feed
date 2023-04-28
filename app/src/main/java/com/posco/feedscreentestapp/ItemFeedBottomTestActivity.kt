package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.screen_feed.databinding.ItemFeedBottomBinding
import testItemFeedBottomUiState

class ItemFeedBottomTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ItemFeedBottomBinding>(
            this,
            com.example.screen_feed.R.layout.item_feed_bottom
        )
        binding.state = testItemFeedBottomUiState(this, binding.root)
    }
}