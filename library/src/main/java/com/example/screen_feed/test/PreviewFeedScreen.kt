package com.example.screen_feed.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsScreenInputEvents
import com.sarang.base_feed.uistate.FeedUiState
import com.sarang.base_feed.uistate.FeedsScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow

@Preview
@Composable
fun PreviewFeedScreen() {
    val data = MutableStateFlow(
        FeedsScreenUiState(
            feeds = ArrayList<FeedUiState>()
        )
    )
    FeedsScreen(uiStateFlow = data, inputEvents = FeedsScreenInputEvents(
        onRefresh = {},
        onProfile = {},
        onShare = {},
        onComment = {},
        onMenu = {},
        onRestaurant = {},
        onAddReview = {},
        onFavorite = {},
        onImage = {},
        onLike = {},
        onName = {}
    ), onBottom = {},
        profileImageServerUrl = "http://sarang628.iptime.org:89/")
}

@Preview
@Composable
fun PreviewEmpty() {
    val data = MutableStateFlow(
        FeedsScreenUiState(
            isEmptyFeed = true
        )
    )
    FeedsScreen(uiStateFlow = data, inputEvents = dummyInput(), onBottom = {}, profileImageServerUrl = "http://sarang628.iptime.org:89/")
}

fun dummyInput() = FeedsScreenInputEvents(
    onName = {},
    onLike = {},
    onImage = {},
    onFavorite = {},
    onAddReview = {},
    onRestaurant = {},
    onMenu = {},
    onComment = {},
    onShare = {},
    onProfile = {},
    onRefresh = {}
)

@Preview
@Composable
fun PreviewRefreshing() {
    val data = MutableStateFlow(
        FeedsScreenUiState(
            isRefreshing = true
        )
    )
    FeedsScreen(uiStateFlow = data, inputEvents = dummyInput(), onBottom = {}, profileImageServerUrl = "http://sarang628.iptime.org:89/")
}

@Preview
@Composable
fun PreviewNetworkError() {
    val data = MutableStateFlow(
        FeedsScreenUiState(
            isFailedConnection = true
        )
    )
    FeedsScreen(uiStateFlow = data, inputEvents = dummyInput(), onBottom = {}, profileImageServerUrl = "http://sarang628.iptime.org:89/")
}