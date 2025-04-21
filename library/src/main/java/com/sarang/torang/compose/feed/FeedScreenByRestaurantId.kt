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
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.adjustHeight
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByRestaurantIdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenByRestaurantId(
    feedsViewModel: FeedScreenByRestaurantIdViewModel = hiltViewModel(),
    restaurantId: Int,
    ontop: Boolean = false,
    consumeOnTop: (() -> Unit)? = null,
    shimmerBrush: @Composable (Boolean) -> Brush,
    feed: @Composable ((feed: Feed, onLike: (Int) -> Unit, onFavorite: (Int) -> Unit, isLogin: Boolean, onVideoClick: () -> Unit, imageHeight: Int) -> Unit),
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
    bottomDetectingLazyColumn: @Composable (Modifier, Int, () -> Unit, @Composable (Int) -> Unit, Boolean, Arrangement.Vertical, LazyListState, @Composable (() -> Unit)?) -> Unit
) {
    val tag = "FeedScreenByRestaurantId"
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
        topAppBar = { /*TODO*/ },
        consumeErrorMessage = { feedsViewModel.clearErrorMsg() },
        onRefresh = { feedsViewModel.refreshFeed() },
        onBottom = { feedsViewModel.onBottom() },
        isRefreshing = isRefreshing,
        onTop = ontop,
        feed = { it ->
            feed(
                it,
                { feedsViewModel.onLike(it) },
                { feedsViewModel.onFavorite(it) },
                isLogin,
                { feedsViewModel.onVideoClick(it.reviewId) },
                it.reviewImages.get(0).adjustHeight(density, screenWidthDp, screenHeightDp)
            )
        },
        consumeOnTop = { consumeOnTop?.invoke() }, shimmerBrush = shimmerBrush,
        pullToRefreshLayout = pullToRefreshLayout,
        bottomDetectingLazyColumn = bottomDetectingLazyColumn
    )
}