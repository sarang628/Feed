package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.RefreshIndicatorState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenInMainViewModel

/**
 * FeedScreen for Main
 *
 * ### Click Event Handling
 * Each item-level click event (e.g., like, share, comment, favorite, etc.)
 * is delegated through [com.sarang.torang.compose.feed.type.LocalFeedCompose].
 * [FeedScreen] handles the like and favorite events directly.
 * For comment and share events, refer to what [com.sarang.torang.compose.feed.type.LocalFeedCompose] provides
 * within the CompositionLocalProvider to understand how they should be handled.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenInMain(
    tag                 : String                        = "__FeedScreenForMain",
    feedsViewModel      : FeedScreenInMainViewModel     = hiltViewModel(),
    feedScreenState     : FeedScreenState               = rememberFeedScreenState(),
    onAddReview         : () -> Unit                    = { Log.w(tag, "onAddReview is not implemented") },
    onAlarm             : () -> Unit                    = { Log.w(tag, "onAlarm is not implemented") },
    scrollEnabled       : Boolean                       = true,
    pageScrollable      : Boolean                       = true,
    contentWindowInsets : WindowInsets                  = ScaffoldDefaults.contentWindowInsets,
) {
    val uiState: FeedLoadingUiState = feedsViewModel.uiState
    val feedUiState: FeedUiState = feedsViewModel.feedUiState
    val appBarState = rememberTopAppBarState()
    var isRefreshing by remember { mutableStateOf(false) }
    val scrollBehavior =
        if (isRefreshing) {
            TopAppBarDefaults.pinnedScrollBehavior(appBarState)
        } else {
            TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
        }

    LaunchedEffect(Unit) {
        if (feedsViewModel.msgState.isNotEmpty()) {
            feedScreenState.showSnackBar(feedsViewModel.msgState[0])
            feedsViewModel.removeTopErrorMessage()
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { feedsViewModel.isRefreshingState }.collect {
            feedScreenState.refresh(it)
        }
    }


    LaunchedEffect(Unit) {
        snapshotFlow {
            feedScreenState.pullToRefreshLayoutState.refreshIndicatorState.value
        }.collect { state ->
            isRefreshing = state != RefreshIndicatorState.Default
        }
    }

    FeedScreen(
        modifier            = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        loadingUiState      = uiState,
        feedUiState         = feedUiState,
        feedScreenState     = feedScreenState,
        feedCallBack        = FeedCallBack(
        onBottom            = { feedsViewModel.onBottom() },
        onRefresh           = { feedsViewModel.refreshFeed(); },
        onFocusItemIndex    = { feedsViewModel.onFocusItemIndex(it) },
        onVideoClick        = { feedsViewModel.onVideoClick(it) },
        onConnect           = { feedsViewModel.reconnect() },
        onLike              = { feedsViewModel.onLike(it) },
        onFavorite          = { feedsViewModel.onFavorite(it) } ),
        scrollEnabled       = scrollEnabled,
        pageScrollable      = pageScrollable,
        contentWindowInsets = contentWindowInsets,
        topAppBar           = { FeedTopAppBar(onAddReview    = onAddReview,
                                              topAppIcon     = Icons.AutoMirrored.Default.Send,
                                              scrollBehavior = scrollBehavior,
                                              onAlarm        = onAlarm) },
    )
}