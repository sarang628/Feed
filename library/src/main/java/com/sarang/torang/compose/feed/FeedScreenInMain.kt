package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feed.component.FeedScreenState
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.compose.feed.component.rememberFeedScreenState
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedsViewModel

/**
 * 메인화면용 FeedScreen
 * 피드 가져오기, 리프레시, 좋아요, 즐겨찾기 기능
 * 피드 프로필, 코멘트, 메뉴 등은 피드 컴포저블을 통해 상위 컴포저블에서 처리
 * @param feedsViewModel 피드 뷰모델
 * @param onAddReview 피드 추가 리뷰
 */
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
    val isLogin by feedsViewModel.isLoginState.collectAsState(initial = false)

    LaunchedEffect(feedsViewModel.msgState) {
        feedScreenState.showSnackBar(feedsViewModel.msgState)
    }

    LaunchedEffect(feedsViewModel.isRefreshingState) {
        feedScreenState.refresh(feedsViewModel.isRefreshingState)
    }

    FeedInMain(
        uiState = uiState,
        feedScreenState = feedScreenState,
        isLogin = isLogin,
        onAddReview = onAddReview,
        onAlarm = onAlarm,
        scrollEnabled = scrollEnabled,
        onBottom = { feedsViewModel.onBottom() },
        onRefresh = { feedsViewModel.refreshFeed(); },
        onFocusItemIndex = { feedsViewModel.onFocusItemIndex(it) },
        onLike = { feedsViewModel.onLike(it) },
        onFavorite = { feedsViewModel.onFavorite(it) },
        onVideoClick = { feedsViewModel.onVideoClick(it) },
        pageScrollable = pageScrollable,
        onConnect = { feedsViewModel.refreshFeed() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedInMain(
    tag              : String          = "__MainFeed",
    uiState          : FeedUiState     = FeedUiState.Loading,
    feedScreenState  : FeedScreenState = rememberFeedScreenState(),
    isLogin          : Boolean         = false,
    pageScrollable   : Boolean         = true,
    scrollEnabled    : Boolean         = true,
    onFocusItemIndex : (Int) -> Unit   = { Log.i(tag, "onFocusItemIndex isn't set") },
    onAlarm          : () -> Unit      = { Log.i(tag, "onAlarm isn't set") },
    onBottom         : () -> Unit      = { Log.i(tag, "onBottom isn't set") },
    onRefresh        : () -> Unit      = { Log.i(tag, "onRefresh isn't set") },
    onAddReview      : () -> Unit      = { Log.i(tag, "onAddReview is not implemented") },
    onLike           : (Int) -> Unit   = { Log.i(tag, "onLike isn't set") },
    onFavorite       : (Int) -> Unit   = { Log.i(tag, "onFavorite isn't set") },
    onVideoClick     : (Int) -> Unit   = { Log.i(tag, "onVideoClick isn't set") },
    onConnect        : () -> Unit      = { Log.i(tag, "onConnect isn't set") },
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    FeedScreen(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        uiState = uiState,
        feedScreenState = feedScreenState,
        onBottom = onBottom,
        onRefresh = onRefresh,
        topAppBar = { FeedTopAppBar(onAddReview = onAddReview, topAppIcon = Icons.AutoMirrored.Default.Send, scrollBehavior = scrollBehavior, onAlarm = onAlarm) },
        onFocusItemIndex = onFocusItemIndex,
        scrollEnabled = scrollEnabled,
        onLike = onLike,
        onFavorite = onFavorite,
        onVideoClick = onVideoClick,
        pageScrollable = pageScrollable,
        isLogin = isLogin,
        onConnect = onConnect
    )
}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    FeedInMain(/*Preview*/
        uiState = FeedUiState.Success(list = listOf(Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample))
    )
}