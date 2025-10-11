package com.sarang.torang.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.viewmodels.FeedScreenInMainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FeedScreenInMainTest() {
    val feedsViewModel: FeedScreenInMainViewModel = hiltViewModel()
    BottomSheetScaffold(
        sheetContent = { Box{
            FlowRow {
                AssistChip(onClick = {feedsViewModel.subScribeFeed()}, label = { Text("subScribeFeed") })
                AssistChip(onClick = {feedsViewModel.loadPage()}, label = { Text("loadPage") })
                AssistChip(onClick = {feedsViewModel.deleteAllFeed()}, label = { Text("deleteAllFeed") })
            }
        } }
    ) {
        FeedScreenInMain(feedsViewModel = feedsViewModel)
    }
}