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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.data.feed.adjustHeight
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByReviewIdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenByReviewId(
    feedsViewModel: FeedScreenByReviewIdViewModel = hiltViewModel(),
    reviewId: Int,
    onTop: Boolean = false,
    consumeOnTop: (() -> Unit)? = null,
    pageScrollable: Boolean = true
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing: Boolean = feedsViewModel.isRefreshingState
    val isLogin: Boolean by feedsViewModel.isLoginState.collectAsState(initial = false)
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(key1 = reviewId) {
        feedsViewModel.getFeedByReviewId(reviewId)
    }

    FeedScreen(
        uiState = uiState,
        topAppBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { backPressedDispatcher?.onBackPressed() }) {
                    Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "")
                }
            })
        },
        consumeErrorMessage = { feedsViewModel.clearErrorMsg() },
        onRefresh = { feedsViewModel.refreshFeed() },
        onBottom = { feedsViewModel.onBottom() },
        isRefreshing = isRefreshing,
        onTop = onTop,
        consumeOnTop = { consumeOnTop?.invoke() },
        isLogin = isLogin,
        onFavorite = { feedsViewModel.onFavorite(it) },
        onLike = { feedsViewModel.onLike(it) },
        onVideoClick = {feedsViewModel.onVideoClick(it)},
        pageScrollable = pageScrollable
    )
}