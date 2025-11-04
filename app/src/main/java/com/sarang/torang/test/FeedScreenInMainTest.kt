package com.sarang.torang.test

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.EmptyTestActivity
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.viewmodels.FeedScreenInMainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FeedScreenInMainTest() {
    val feedsViewModel: FeedScreenInMainViewModel = hiltViewModel()
    val context : Context = LocalContext.current
    BottomSheetScaffold(
        sheetContent = { Box{
            FlowRow {
                AssistChip(
                    onClick = {feedsViewModel.subScribeFeed()},
                    label = { Text("subScribeFeed") }
                )
                AssistChip(
                    onClick = {feedsViewModel.loadPage()},
                    label = { Text("loadPage") }
                )
                AssistChip(
                    onClick = {feedsViewModel.deleteAllFeed()},
                    label = { Text("deleteAllFeed") }
                )
                AssistChip(
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                EmptyTestActivity::class.java
                            )
                        )
                    },
                    label = { Text("newActivity") }
                )
            }
        } }
    ) {
        FeedScreenInMain(feedsViewModel = feedsViewModel)
    }
}