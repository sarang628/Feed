package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedsViewModel

/**
 * 메인화면용 FeedScreen
 * 피드 가져오기, 리프레시, 좋아요, 즐겨찾기 기능
 * 피드 프로필, 코멘트, 메뉴 등은 피드 컴포저블을 통해 상위 컴포저블에서 처리
 * @param feedsViewModel 피드 뷰모델
 * @param onAddReview 피드 추가 리뷰
 * @param scrollToTop 피드 스크롤 탑
 * @param onScrollToTop 스크롤 탑 콜백 (이 콜백을 받으면 scrollToTop을 false로 바꿔줘야 함.)
 */
@Composable
fun FeedScreenInMain(
    tag: String = "__FeedScreenForMain",
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    onAddReview: (() -> Unit) = { Log.w(tag, "onAddReview is not implemented") },
    onAlarm: () -> Unit = { Log.w("__FeedScreenForMain", "onAlarm is not implemented") },
    scrollToTop: Boolean,
    onScrollToTop: () -> Unit,
    scrollEnabled: Boolean = true,
    pageScrollable: Boolean = true
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing: Boolean = feedsViewModel.isRefreshingState
    val isLogin by feedsViewModel.isLoginState.collectAsState(initial = false)
    val showReConnect = feedsViewModel.showReConnect

    FeedInMain(
        uiState = uiState,
        errorMsg = feedsViewModel.msgState,
        isLogin = isLogin,
        onAddReview = onAddReview,
        onAlarm = onAlarm,
        isRefreshing = isRefreshing,
        onTop = scrollToTop,
        consumeOnTop = onScrollToTop,
        scrollEnabled = scrollEnabled,
        onBottom = { feedsViewModel.onBottom() },
        onRefresh = { feedsViewModel.refreshFeed() },
        consumeErrorMessage = { feedsViewModel.clearErrorMsg() },
        onFocusItemIndex = { feedsViewModel.onFocusItemIndex(it) },
        onLike = { feedsViewModel.onLike(it) },
        onFavorite = { feedsViewModel.onFavorite(it) },
        onVideoClick = { feedsViewModel.onVideoClick(it) },
        pageScrollable = pageScrollable,
        showReConnect = showReConnect,
        onConnect = { feedsViewModel.refreshFeed() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedInMain(
    tag:                    String = "__MainFeed",
    uiState:                FeedUiState,
    errorMsg:               String = "",
    isLogin:                Boolean = false,
    pageScrollable:         Boolean = true,
    isRefreshing:           Boolean,
    onTop:                  Boolean,
    topAppIcon:             ImageVector   = Icons.AutoMirrored.Default.Send,
    scrollEnabled:          Boolean       = true,
    showReConnect:          Boolean       = false,
    onFocusItemIndex:       (Int) -> Unit = {},
    consumeErrorMessage:    () -> Unit    = {},
    onAlarm:                () -> Unit    = { Log.w(tag, "onAlarm is not implemented") },
    onBottom:               () -> Unit    = {},
    onRefresh:              () -> Unit    = {},
    consumeOnTop:           () -> Unit    = {},
    onAddReview:            () -> Unit    = { Log.w(tag, "onAddReview is not implemented") },
    onLike :                (Int) -> Unit = {},
    onFavorite:             (Int) -> Unit = {},
    onVideoClick :          (Int) -> Unit = {},
    onConnect :             () -> Unit    = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    FeedScreen(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        uiState = uiState,
        errorMsg = errorMsg,
        consumeErrorMessage = consumeErrorMessage,
        onBottom = onBottom,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        onTop = onTop,
        consumeOnTop = consumeOnTop,
        topAppBar = { FeedTopAppBar(onAddReview = onAddReview, topAppIcon = topAppIcon, scrollBehavior = scrollBehavior, onAlarm = onAlarm) },
        onFocusItemIndex = onFocusItemIndex,
        scrollEnabled = scrollEnabled,
        onLike = onLike,
        onFavorite = onFavorite,
        onVideoClick = onVideoClick,
        pageScrollable = pageScrollable,
        isLogin = isLogin,
        showReConnect = showReConnect,
        onConnect = onConnect
    )
}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    FeedInMain(/*Preview*/
        uiState = FeedUiState.Success(list = listOf(Feed.Sample),),
        onAddReview = {},
        consumeErrorMessage = {},
        onRefresh = {},
        onBottom = {},
        isRefreshing = false,
        onTop = false,
        consumeOnTop = {}
    )
}