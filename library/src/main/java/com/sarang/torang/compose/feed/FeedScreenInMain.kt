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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.viewmodels.FeedsViewModel

@Composable
fun FeedScreenInMain(
    tag             : String            = "__FeedScreenForMain",
    feedsViewModel  : FeedsViewModel    = hiltViewModel(),
    feedScreenState : FeedScreenState   = rememberFeedScreenState(),
    onAddReview     : () -> Unit        = { Log.w(tag, "onAddReview is not implemented") },
    onAlarm         : () -> Unit        = { Log.w(tag, "onAlarm is not implemented") },
    scrollEnabled   : Boolean           = true,
    pageScrollable  : Boolean           = true
) {
    val uiState: FeedLoadingUiState by feedsViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(feedsViewModel.msgState) {
        if (feedsViewModel.msgState.isNotEmpty()) {
            feedScreenState.showSnackBar(feedsViewModel.msgState[0])
            feedsViewModel.removeTopErrorMessage()
        }
    }
    LaunchedEffect(feedsViewModel.isRefreshingState) { feedScreenState.refresh(feedsViewModel.isRefreshingState) }

    FeedInMain(
        uiState = uiState,
        feedScreenState = feedScreenState,
        onAddReview = onAddReview,
        feedCallBack = FeedCallBack(
        onBottom = { feedsViewModel.onBottom() },
        onRefresh = { feedsViewModel.refreshFeed(); },
        onFocusItemIndex = { feedsViewModel.onFocusItemIndex(it) },
        onVideoClick = { feedsViewModel.onVideoClick(it) },
        onConnect = { feedsViewModel.refreshFeed() }),
        onAlarm = onAlarm,
        scrollEnabled = scrollEnabled,
        pageScrollable = pageScrollable
    )
}

/**
 * Main 용 FeedScreen
 * 상단에 TopAppBar와 스크롤 시 상호작용 하는 기능이 적용 됨.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedInMain(
    tag              : String          = "__MainFeed",
    uiState          : FeedLoadingUiState     = FeedLoadingUiState.Loading,
    feedScreenState  : FeedScreenState = rememberFeedScreenState(),
    pageScrollable   : Boolean         = true,
    scrollEnabled    : Boolean         = true,
    feedCallBack     : FeedCallBack    = FeedCallBack(),
    onAddReview      : () -> Unit      = { Log.i(tag, "onAddReview is not implemented") },
    onAlarm          : () -> Unit      = { Log.i(tag, "onAlarm is not implemented") },
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    FeedScreen(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        uiState = uiState,
        feedScreenState = feedScreenState,
        feedCallBack = feedCallBack,
        topAppBar = { FeedTopAppBar(onAddReview = onAddReview, topAppIcon = Icons.AutoMirrored.Default.Send, scrollBehavior = scrollBehavior, onAlarm = onAlarm) },
        scrollEnabled = scrollEnabled,
        pageScrollable = pageScrollable,
    )
}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    FeedInMain(/*Preview*/
        uiState = FeedLoadingUiState.Success
    )
}