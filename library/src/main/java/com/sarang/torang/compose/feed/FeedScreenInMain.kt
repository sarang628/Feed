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
import com.sarang.torang.uistate.FeedUiState
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
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsStateWithLifecycle()
    val isLogin: Boolean by feedsViewModel.isLoginState.collectAsStateWithLifecycle(false)
    LaunchedEffect(feedsViewModel.msgState) { feedScreenState.showSnackBar(feedsViewModel.msgState) }
    LaunchedEffect(feedsViewModel.isRefreshingState) { feedScreenState.refresh(feedsViewModel.isRefreshingState) }

    FeedInMain(
        uiState = uiState,
        feedScreenState = feedScreenState,
        isLogin = isLogin,
        onAddReview = onAddReview,
        feedCallBack = FeedCallBack(
        onBottom = { feedsViewModel.onBottom() },
        onRefresh = { feedsViewModel.refreshFeed(); },
        onFocusItemIndex = { feedsViewModel.onFocusItemIndex(it) },
        onLike = { feedsViewModel.onLike(it) },
        onFavorite = { feedsViewModel.onFavorite(it) },
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
    uiState          : FeedUiState     = FeedUiState.Loading,
    feedScreenState  : FeedScreenState = rememberFeedScreenState(),
    isLogin          : Boolean         = false,
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
        isLogin = isLogin,
    )
}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    FeedInMain(/*Preview*/
        uiState = FeedUiState.Success(list = listOf(Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample))
    )
}