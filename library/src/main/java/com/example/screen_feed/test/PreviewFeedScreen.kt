package com.example.screen_feed.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.screen_feed.FeedsScreen
import com.sarang.base_feed.data.Feed
import com.sarang.base_feed.uistate.FeedsScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow

@Preview
@Composable
fun PreviewFeedScreen() {
    val data = MutableStateFlow(FeedsScreenUiState(
        feeds = ArrayList<Feed>().apply {
            add(
                Feed(
                    name = "monkey",
                    restaurantName = "Mcdonalds",
                    isFavorite = true,
                    isLike = true,
                    rating = 3.5f,
                    likeAmount = 10,
                    commentAmount = 10,
                    author = "bb",
                    author1 = "cc",
                    author2 = "dd",
                    comment = "aaaa",
                    comment1 = "bbbb",
                    comment2 = "cccc",
                    reviewImages = ArrayList<String>().apply {
                        add("")
                        add("")
                        add("")
                    },
                    contents = "abcd"
                )
            )
        }
    ))
    FeedsScreen(uiStateFlow = data, inputEvents = null)
}

@Preview
@Composable
fun PreviewEmpty() {
    val data = MutableStateFlow(
        FeedsScreenUiState(
            isEmptyFeed = true
        )
    )
    FeedsScreen(uiStateFlow = data, inputEvents = null)
}

@Preview
@Composable
fun PreviewRefreshing() {
    val data = MutableStateFlow(
        FeedsScreenUiState(
            isRefreshing = true
        )
    )
    FeedsScreen(uiStateFlow = data, inputEvents = null)
}

@Preview
@Composable
fun PreviewNetworkError() {
    val data = MutableStateFlow(
        FeedsScreenUiState(
            isFailedConnection = true
        )
    )
    FeedsScreen(uiStateFlow = data, inputEvents = null)
}