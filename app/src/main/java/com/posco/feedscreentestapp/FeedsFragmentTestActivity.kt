package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsViewModel
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
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

@Composable
fun FeedsScreen(feedsViewModel: FeedsViewModel){
    val ss by feedsViewModel.uiState.collectAsState()
    FeedsScreen(uiState = ss, onRefresh = {
        feedsViewModel.refresh()
    })
}