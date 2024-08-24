package com.sarang.torang.compose.feed

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByReviewIdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenByReviewId(
    feedsViewModel: FeedScreenByReviewIdViewModel = hiltViewModel(),
    reviewId: Int,
    ontop: Boolean = false,
    consumeOnTop: (() -> Unit)? = null,
    shimmerBrush: @Composable (Boolean) -> Brush,
    feed: @Composable ((
        feed: Feed,
        onLike: (Int) -> Unit,
        onFavorite: (Int) -> Unit,
        isLogin: Boolean,
    ) -> Unit),
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
) {

    val uiState: FeedUiState = feedsViewModel.uiState
    val isRefreshing: Boolean = feedsViewModel.isRefreshing
    val isLogin: Boolean by feedsViewModel.isLogin.collectAsState(initial = false)

    LaunchedEffect(key1 = reviewId) {
        feedsViewModel.getFeedByReviewId(reviewId)
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
                isLogin
            )
        },
        consumeOnTop = { consumeOnTop?.invoke() },
        shimmerBrush = shimmerBrush,
        pullToRefreshLayout = pullToRefreshLayout
    )
}