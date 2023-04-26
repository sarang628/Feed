package com.posco.feedscreentestapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.screen_feed.adapters.FeedPagerAdapter
import com.example.screen_feed.adapters.FeedsRecyclerViewAdapter
import com.example.screen_feed.uistate.ItemFeedBottomUIState
import com.example.screen_feed.uistate.ItemFeedTopUIState
import com.example.screen_feed.uistate.ItemFeedUIState
import com.google.android.material.snackbar.Snackbar
import com.posco.feedscreentestapp.databinding.ActivityItemTimeLineListTestBinding

class ItemTimeLineListTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityItemTimeLineListTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val useCase = testItemTimeLineUiState(this, binding.root)

        binding.rv.apply {
            adapter = FeedsRecyclerViewAdapter().apply {
                setFeeds(
                    arrayListOf(useCase, useCase, useCase)
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