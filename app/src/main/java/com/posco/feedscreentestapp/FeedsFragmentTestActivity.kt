package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedsFragmentTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedsScreen(feedsViewModel = FeedsViewModel(this))
        }
    }
}