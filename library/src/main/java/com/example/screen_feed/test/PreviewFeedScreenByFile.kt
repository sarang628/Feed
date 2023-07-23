package com.example.screen_feed.test

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.FeedsScreen
import com.sarang.base_feed.data.Feed
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

    var count by remember { mutableStateOf(0) }

    val d = remember { data }

    FeedsScreen(
        uiStateFlow = d,
        inputEvents = null,
        onBottom = {
            d.value = d.value.copy(
                feeds = ArrayList<Feed>().apply {
                    d.value.feeds?.let { it1 -> addAll(it1) }
                    addAll(list)
                }
            )
            count++
        },
        snackBar = "${count}th feed loading"
    )
}