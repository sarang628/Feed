package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.screen_feed.databinding.ItemFeedTopBinding


class ItemFeedTopTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ItemFeedTopBinding>(
            this,
            com.example.screen_feed.R.layout.item_feed_top
        )
        binding.state = testItemFeedTopUIState(this, binding.root)
    }
}