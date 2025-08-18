package com.sarang.torang.compose.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.data.feed.adjustHeight
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByRestaurantIdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenByRestaurantId(
    feedsViewModel: FeedScreenByRestaurantIdViewModel = hiltViewModel(),
    restaurantId: Int,
    onTop: Boolean = false,
    consumeOnTop: (() -> Unit)? = null,
    pageScrollable : Boolean = true
) {
    val uiState: FeedUiState = feedsViewModel.uiState
    val isRefreshing: Boolean = feedsViewModel.isRefreshing
    val isLogin by feedsViewModel.isLogin.collectAsState(initial = false)
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current

    LaunchedEffect(key1 = restaurantId) {
        feedsViewModel.getFeedByRestaurantId(restaurantId)
    }

    FeedScreen(
        uiState = uiState,
        consumeErrorMessage = { feedsViewModel.clearErrorMsg() },
        onRefresh = { feedsViewModel.refreshFeed() },
        onBottom = { feedsViewModel.onBottom() },
        isRefreshing = isRefreshing,
        onTop = onTop,
        feed = { it ->
            LocalFeedCompose.current(
                it,
                { feedsViewModel.onLike(it) },
                { feedsViewModel.onFavorite(it) },
                isLogin,
                { feedsViewModel.onVideoClick(it.reviewId) },
                it.reviewImages[0].adjustHeight(density, screenWidthDp, screenHeightDp),
                pageScrollable
            )
        },
        consumeOnTop = { consumeOnTop?.invoke() },
    )
}