package com.example.screen_feed.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.data.Feed
import com.sarang.base_feed.uistate.FeedsScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow

@Preview
@Composable
fun PreviewFeedScreenByFile() {
    val list = JsonToObjectGenerator<Feed>()
        .getListByFile(LocalContext.current, "feeds.json", Feed::class.java)
    val data = MutableStateFlow(FeedsScreenUiState(
        feeds = ArrayList<Feed>().apply {
            addAll(list)
        }
    ))

    FeedsScreen(uiStateFlow = data, inputEvents = null)
}