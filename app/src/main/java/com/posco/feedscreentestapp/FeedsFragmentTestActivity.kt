package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.uistate.FeedUiState
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedsFragmentTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val uiState =
                getTestSenarioFeedFragmentUIstate(
                    this@FeedsFragmentTestActivity,
                    this@FeedsFragmentTestActivity
                )
            setContent {
                test1(uiState = uiState)
            }
        }
    }
}

@Composable
fun test1(uiState: StateFlow<FeedsScreenUiState>) {
    val ss by uiState.collectAsState()
    FeedsScreen(uiState = ss)

}