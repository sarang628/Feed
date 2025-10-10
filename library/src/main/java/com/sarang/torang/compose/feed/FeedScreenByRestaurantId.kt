package com.sarang.torang.compose.feed

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByRestaurantIdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenByRestaurantId(
    feedsViewModel: FeedScreenByRestaurantIdViewModel = hiltViewModel(),
    restaurantId: Int,
    pageScrollable : Boolean = true,
    feedScreenState: FeedScreenState = rememberFeedScreenState()
) {
    val uiState: FeedLoadingUiState = feedsViewModel.uiState
    val feedUiState: FeedUiState = feedsViewModel.feedUiState

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
            onRefresh = { feedsViewModel.refreshFeed() },
            onBottom = { feedsViewModel.onBottom() },
            onVideoClick = { feedsViewModel.onVideoClick(it) },
        ),
        pageScrollable = pageScrollable
    )
}