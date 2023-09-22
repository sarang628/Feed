package com.example.screen_feed.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.FeedsScreen
import com.sarang.base_feed.uistate.FeedUiState
import com.sarang.base_feed.uistate.FeedsScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow

@Preview
@Composable
fun PreviewFeedScreenByFile() {
    FeedScreenByFile()
}

@Composable
fun FeedScreenByFile() {
    val list = JsonToObjectGenerator<FeedUiState>()
        .getListByFile(LocalContext.current, "feeds.json", FeedUiState::class.java)
    val data = MutableStateFlow(FeedsScreenUiState(
        feeds = ArrayList<FeedUiState>().apply {
            addAll(list)
        }
    ))

    var count by remember { mutableStateOf(0) }

    val d = remember { data }

    FeedsScreen(
        uiStateFlow = d,
        inputEvents = dummyInput(),
        onBottom = {
            d.value = d.value.copy(
                feeds = ArrayList<FeedUiState>().apply {
                    d.value.feeds?.let { it1 -> addAll(it1) }
                    addAll(list)
                }
            )
            count++
        },
        snackBar = "${count}th feed loading",
        imageServerUrl = "http://sarang628.iptime.org:89/review_images/"
    )
}