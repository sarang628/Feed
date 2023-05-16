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
fun Feed(
    uiState: FeedUiState,
    onProfile: ((Int) -> Unit)? = null,
    onLike: ((Int) -> Unit)? = null,
    onComment: ((Int) -> Unit)? = null,
    onShare: ((Int) -> Unit)? = null,
    onFavorite: ((Int) -> Unit)? = null,
    onMenu: ((Int) -> Unit)? = null,
    onName: ((Int) -> Unit)? = null,
    onRestaurant: ((Int) -> Unit)? = null,
    onImage: ((Int) -> Unit)? = null,
) {
    Column {
        ItemFeedTop(
            uiState.itemFeedTopUiState,
            onProfile = onProfile,
            onMenu = onMenu,
            onName = onName,
            onRestaurant = onRestaurant
        )
        Spacer(modifier = Modifier.height(4.dp))
        ItemFeedMid(uiState.reviewImages, onImage = onImage)
        ItemFeedBottom(
            uiState = uiState.itemFeedBottomUiState,
            onLike = onLike,
            onComment = onComment,
            onShare = onShare,
            onFavorite = onFavorite
        )
    }
}


@Preview
@Composable
fun PreViewItemFeed() {
    val list = getFeedsByFile(LocalContext.current)
    Feed(uiState = list[0].FeedUiState())
}
