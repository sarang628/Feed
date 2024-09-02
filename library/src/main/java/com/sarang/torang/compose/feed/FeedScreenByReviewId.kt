package com.sarang.torang.compose.feed

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.adjustHeight
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
        onVideoClick: () -> Unit,
        imageHeight: Int,
    ) -> Unit),
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
) {

    val uiState: FeedUiState = feedsViewModel.uiState
    val isRefreshing: Boolean = feedsViewModel.isRefreshing
    val isLogin: Boolean by feedsViewModel.isLogin.collectAsState(initial = false)
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current

    LaunchedEffect(key1 = reviewId) {
        feedsViewModel.getFeedByReviewId(reviewId)
    }

    FeedScreen(
        uiState = uiState,
        topAppBar = {
            TopAppBar(title = { /*TODO*/ }, navigationIcon = {
                IconButton(onClick = { backPressedDispatcher?.onBackPressed() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = ""
                    )
                }
            })
        },
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
        consumeOnTop = { consumeOnTop?.invoke() },
        shimmerBrush = shimmerBrush,
        pullToRefreshLayout = pullToRefreshLayout
    )
}