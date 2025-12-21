package com.sarang.torang

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Menu(
    onFeedScreen: () -> Unit = {},
    onLoginRepository: () -> Unit = {},
    onFeedScreenInMain: () -> Unit = {},
    onFeed: () -> Unit = {},
    onFeedScreenByRestaurantId: () -> Unit = {},
    onFeedScreenByPictureId: () -> Unit = {},
    onFeedScreenByReviewId: () -> Unit = {},
) {
    Column {
        Button(onFeedScreen) { Text("FeedScreen") }
        Button(onLoginRepository) { Text("LoginRepository") }
        Button(onFeedScreenInMain) { Text("FeedScreenInMain") }
        Button(onFeed) { Text("Feed") }
        Button(onFeedScreenByRestaurantId) { Text("FeedScreenByRestaurantId") }
        Button(onFeedScreenByPictureId) { Text("FeedScreenByPictureId") }
        Button(onFeedScreenByReviewId) { Text("FeedScreenByReviewId") }
    }
}