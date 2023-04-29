package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.FeedUiState
import com.example.screen_feed.uistate.getAdapter
import testItemFeedUiState

class FeedTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ItemTimeLineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val useCase: FeedUiState = testItemFeedUiState(this, binding.root)
        binding.useCase = useCase
        binding.viewpager.adapter = useCase.getAdapter()
    }
}