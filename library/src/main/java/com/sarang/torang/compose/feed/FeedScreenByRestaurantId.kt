package com.sarang.torang.compose.feed

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.feed.data.FeedCallBack
import com.sarang.torang.compose.feed.data.FeedScreenConfig
import com.sarang.torang.compose.feed.FeedScreenByRestaurantIdViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenByRestaurantId(
    feedsViewModel  : FeedScreenByRestaurantIdViewModel = hiltViewModel(),
    restaurantId    : Int,
    feedScreenConfig: FeedScreenConfig = FeedScreenConfig(),
    feedScreenState : FeedScreenState = rememberFeedScreenState()
) {
    val uiState         : FeedLoadingUiState    = feedsViewModel.uiState
    val feedUiState     : FeedUiState           = feedsViewModel.feedUiState

    LaunchedEffect(key1 = restaurantId) {
        feedsViewModel.getFeedByRestaurantId(restaurantId)
    }

    LaunchedEffect(feedsViewModel.msgState) {
        if(!feedsViewModel.msgState.isEmpty()){
            feedScreenState.showSnackBar(feedsViewModel.msgState[0])
            feedsViewModel.removeTopErrorMessage();
        }
    }

    FeedScreen(
        loadingUiState = uiState,
        feedUiState = feedUiState,
        feedCallBack = FeedCallBack(
            onBottom = feedsViewModel::onBottom,
            onRefresh = feedsViewModel::refreshFeed,
            onVideoClick = feedsViewModel::onVideoClick,
            onConnect = feedsViewModel::reconnect,
            onLike = feedsViewModel::onLike,
            onFavorite = feedsViewModel::onFavorite
        ),
        feedScreenConfig = feedScreenConfig
    )
}