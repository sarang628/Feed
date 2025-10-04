package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenInMainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenInMain(
    tag             : String                        = "__FeedScreenForMain",
    feedsViewModel  : FeedScreenInMainViewModel     = hiltViewModel(),
    feedScreenState : FeedScreenState               = rememberFeedScreenState(),
    onAddReview     : () -> Unit                    = { Log.w(tag, "onAddReview is not implemented") },
    onAlarm         : () -> Unit                    = { Log.w(tag, "onAlarm is not implemented") },
    scrollEnabled   : Boolean                       = true,
    pageScrollable  : Boolean                       = true
) {
    val uiState: FeedLoadingUiState = feedsViewModel.uiState
    val feedUiState: FeedUiState by feedsViewModel.feedUiState.collectAsStateWithLifecycle()
    LaunchedEffect(feedsViewModel.msgState) {
        if (feedsViewModel.msgState.isNotEmpty()) {
            feedScreenState.showSnackBar(feedsViewModel.msgState[0])
            feedsViewModel.removeTopErrorMessage()
        }
    }
    LaunchedEffect(feedsViewModel.isRefreshingState) { feedScreenState.refresh(feedsViewModel.isRefreshingState) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    FeedScreen(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        loadingUiState = FeedLoadingUiState.Reconnect,
//        feedUiState = feedUiState,
//        feedScreenState = feedScreenState,
//        feedCallBack = FeedCallBack(
//            onBottom = { feedsViewModel.onBottom() },
//            onRefresh = { feedsViewModel.refreshFeed(); },
//            onFocusItemIndex = { feedsViewModel.onFocusItemIndex(it) },
//            onVideoClick = { feedsViewModel.onVideoClick(it) },
//            onConnect = { feedsViewModel.refreshFeed() },
//            onLike = {feedsViewModel.onLike(it)},
//            onFavorite = {feedsViewModel.onFavorite(it)}
//        ),
//        topAppBar = { FeedTopAppBar(onAddReview = onAddReview, topAppIcon = Icons.AutoMirrored.Default.Send, scrollBehavior = scrollBehavior, onAlarm = onAlarm) },
//        scrollEnabled = scrollEnabled,
//        pageScrollable = pageScrollable,
    )
}