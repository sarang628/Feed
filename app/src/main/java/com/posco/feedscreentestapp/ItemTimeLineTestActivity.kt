package com.posco.feedscreentestapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.screen_feed.adapters.FeedPagerAdapter
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.ItemFeedBottomUIState
import com.example.screen_feed.uistate.ItemFeedTopUIState
import com.example.screen_feed.uistate.ItemFeedUIState
import com.example.screen_feed.usecase.*
import com.google.android.material.snackbar.Snackbar

class ItemTimeLineTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ItemTimeLineBinding>(
            this,
            com.example.screen_feed.R.layout.item_time_line
        )

        val useCase = testItemTimeLineUiState(this, binding.root)

        binding.useCase = useCase
    }

    fun snackBar(view: View, text: String) {
        Snackbar.make(
            this, view, text, Snackbar.LENGTH_SHORT
        ).show()
    }
}