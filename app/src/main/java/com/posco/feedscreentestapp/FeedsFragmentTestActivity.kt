package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.screen_feed.TestFeedsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedsFragmentTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestFeedsScreen()
        }
    }
}