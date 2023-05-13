package com.example.screen_feed.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.screen_feed.uistate.FeedUiState
import com.example.screen_feed.uistate.getFeedsByFile

@Composable
fun ItemFeed(uiState: FeedUiState) {
    Column() {
        ItemFeedTop(uiState.itemFeedTopUiState)
        Spacer(modifier = Modifier.height(4.dp))
        ItemFeedMid(uiState.reviewImages)
        ItemFeedBottom(uiState.itemFeedBottomUiState)
    }
}


@Preview
@Composable
fun preViewItemFeed() {
    var list = getFeedsByFile(LocalContext.current)
    ItemFeed(uiState = list[0].FeedUiState())
}
