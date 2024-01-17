package com.sarang.torang.di.feed_di

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.FeedScreen
import com.sryang.torang.compose.feed.Feeds
import com.sryang.torang.uistate.FeedsUiState

@Composable
fun ProvideFeedScreen(
    onAddReview: (() -> Unit),
    ratingBar: @Composable (Modifier, Float) -> Unit
) {
    val TAG = "_ProvideFeedScreen"
    FeedScreen(
        onAddReview = onAddReview,
        feeds = { list, onRefresh, onBottom, isRefreshing ->
            Feeds(
                onRefresh = onRefresh,
                onBottom = onBottom,
                ratingBar = { modifier, float -> ratingBar(modifier, float) },
                isRefreshing = isRefreshing,
                feedsUiState = FeedsUiState.Success(
                    list.map {
                        it.review(
                            onProfile = { Log.d(TAG, "onProfile()") },
                            onImage = { Log.d(TAG, "onImage()") },
                            onRestaurant = { Log.d(TAG, "onRestaurant()") },
                            onName = { Log.d(TAG, "onName()") },
                            onMenu = { Log.d(TAG, "onMenu()") },
                            onShare = { Log.d(TAG, "onShare()") },
                            onComment = { Log.d(TAG, "onComment()") }
                        )
                    }
                )
            )
        }
    )
}
