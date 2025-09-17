package com.sarang.torang.compose.feed

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feed.component.FeedScreenState
import com.sarang.torang.compose.feed.component.rememberFeedScreenState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByRestaurantIdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenByRestaurantId(
    feedsViewModel: FeedScreenByRestaurantIdViewModel = hiltViewModel(),
    restaurantId: Int,
    onTop: Boolean = false,
    consumeOnTop: (() -> Unit)? = null,
    pageScrollable : Boolean = true,
    feedScreenState: FeedScreenState = rememberFeedScreenState()
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing: Boolean = feedsViewModel.isRefreshingState
    val isLogin by feedsViewModel.isLoginState.collectAsState(initial = false)

    LaunchedEffect(key1 = restaurantId) {
        feedsViewModel.getFeedByRestaurantId(restaurantId)
    }

    LaunchedEffect(feedsViewModel.msgState) {
        feedScreenState.showSnackBar(feedsViewModel.msgState)
    }

    FeedScreen(
        uiState = uiState,
        feedCallBack = FeedCallBack(
            onRefresh = { feedsViewModel.refreshFeed() },
            onBottom = { feedsViewModel.onBottom() },
            onFavorite = { feedsViewModel.onFavorite(it) },
            onVideoClick = { feedsViewModel.onVideoClick(it) },
            onLike = { feedsViewModel.onLike(it) },
        ),
        isLogin = isLogin,
        pageScrollable = pageScrollable
    )
}