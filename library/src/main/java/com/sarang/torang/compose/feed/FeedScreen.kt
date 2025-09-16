package com.sarang.torang.compose.feed

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.compose.feed.component.EmptyFeed
import com.sarang.torang.compose.feed.component.FeedScreenState
import com.sarang.torang.compose.feed.component.FeedShimmer
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.compose.feed.component.PullToRefreshLayoutState
import com.sarang.torang.compose.feed.component.RefreshAndBottomDetectionLazyColumn
import com.sarang.torang.compose.feed.component.rememberFeedScreenState
import com.sarang.torang.compose.feed.component.rememberPullToRefreshState
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.imageHeight
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 피드 화면
 * @param uiState
 * @param consumeErrorMessage snackBar에서 메세지 소비
 * @param topAppBar 상단 앱 바
 * @param feed 피드 항목 UI
 * @param onBottom 하단 터치 이벤트
 * @param isRefreshing 피드 갱신중 여부
 * @param onRefresh 피드 갱신 요청
 * @param onBackToTop back 이벤트 시 리스트를 최상단으로 올리는 이벤트
 * @param onFocusItemIndex 비디오 재생을 위해 항목이 중앙에 있을때 호출되는 콜백
 * @param scrollEnabled 리스트 스크롤 가능 여부
 * @sample FeedScreenEmptyPreview
 * @sample FeedScreenLoadingPreview
 * @sample FeedScreenSuccessPreview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    // @formatter:off
    modifier            :Modifier               = Modifier,
    feedScreenState     :FeedScreenState        = rememberFeedScreenState(),
    tag                 :String                 = "__FeedScreen",
    uiState             :FeedUiState            = FeedUiState.Loading,
    topAppBar           :@Composable () -> Unit = { Log.i(tag, "topAppBar is not set") },
    onBackToTop         :Boolean                = true,
    scrollEnabled       :Boolean                = true,
    pageScrollable      :Boolean                = true,
    isLogin             :Boolean                = false,
    onBottom            :() -> Unit             = { Log.i(tag, "onBottom is not set") },
    onRefresh           :() -> Unit             = { Log.i(tag, "onRefresh is not set") },
    onFocusItemIndex    :(Int) -> Unit          = { Log.i(tag, "onFocusItemIndex is not set") },
    onLike              :(Int) -> Unit          = {},
    onFavorite          :(Int) -> Unit          = {},
    onVideoClick        :(Int) -> Unit          = {},
    onConnect           :() -> Unit             = {}
    // @formatter:on
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = feedScreenState.snackbarState) },
        topBar = topAppBar
    ) {
        Box(Modifier.fillMaxWidth()){
            AnimatedVisibility(uiState == FeedUiState.Loading, enter = fadeIn(tween(durationMillis = 1000)), exit = fadeOut(tween(durationMillis = 1000))) {
                FeedShimmer(modifier = Modifier.fillMaxSize().padding(it))
            }

            AnimatedVisibility(uiState == FeedUiState.Empty, enter = fadeIn(tween(durationMillis = 1000))) {
                RefreshAndBottomDetectionLazyColumn(
                    modifier = Modifier.padding(it),
                    count = 0,
                    onBottom = {},
                    listState = feedScreenState.listState,
                    userScrollEnabled = scrollEnabled,
                    onRefresh = onRefresh,
                    contents = { EmptyFeed() }) { }
            }

            AnimatedVisibility(uiState is FeedUiState.Success, enter = fadeIn(tween(durationMillis = 1000))) {
                if(uiState is FeedUiState.Success) {
                    FeedSuccess(modifier = modifier.padding(it),
                        listState = feedScreenState.listState,
                        scrollEnabled = scrollEnabled,
                        pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
                        uiState = uiState,
                        onRefresh = onRefresh,
                        onLike = onLike,
                        onFavorite = onFavorite,
                        onVideoClick = onVideoClick,
                        pageScrollable = pageScrollable,
                        isLogin = isLogin)
                }
            }

            if(uiState == FeedUiState.Reconnect) {
                Button(modifier = Modifier.align(Alignment.Center), onClick = onConnect) {
                    Text("connect")
                }
            }
        }
    }
    HandleOnFocusIndex(feedScreenState.listState, onFocusItemIndex)
    HandleBack(feedScreenState.listState, onBackToTop)
}

@Composable
private fun FeedSuccess(modifier : Modifier = Modifier,
                        tag             :String                 = "__FeedSuccess",
                        uiState         :FeedUiState.Success    = FeedUiState.Success(listOf()),
                        onBottom        :() -> Unit             = { Log.i(tag, "onBottom is not set") },
                        scrollEnabled   :Boolean                = true,
                        listState       :LazyListState          = rememberLazyListState(),
                        pullToRefreshLayoutState: PullToRefreshLayoutState = rememberPullToRefreshState(),
                        onRefresh       :() -> Unit             = { Log.i(tag, "onRefresh is not set") },
                        onLike          :(Int) -> Unit          = {},
                        onFavorite      :(Int) -> Unit          = {},
                        onVideoClick    :(Int) -> Unit          = {},
                        pageScrollable  :Boolean                = true,
                        isLogin         :Boolean                = false,
                        ){
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current
    RefreshAndBottomDetectionLazyColumn(
        modifier = modifier,
        count = uiState.list.size,
        onBottom = onBottom,
        userScrollEnabled = scrollEnabled,
        pullToRefreshLayoutState = pullToRefreshLayoutState,
        listState = listState,
        onRefresh = onRefresh
    ) {
        val feed =
            uiState.list[it].copy(/*isPlaying = it.isPlaying && if (uiState is FeedUiState.Success) { (uiState as FeedUiState.Success).focusedIndex == (uiState as FeedUiState.Success).list.indexOf(it) } else false*/)
        var imageHeight = uiState.imageHeight(density, screenWidthDp, screenHeightDp)
        LocalFeedCompose.current.invoke(
            feed,
            onLike,
            onFavorite,
            isLogin,
            { onVideoClick.invoke(uiState.list[it].reviewId) },
            imageHeight,
            pageScrollable
        )
    }
}

@Composable
private fun HandleOnFocusIndex(listState: LazyListState, onFocusItemIndex: (Int) -> Unit) {
    var currentFocusItem by remember { mutableIntStateOf(-1) }
    LaunchedEffect(key1 = listState) {
        snapshotFlow {
            listState.firstVisibleItemScrollOffset
        }.collect {
            if (listState.firstVisibleItemIndex == 0 && it < 500 && currentFocusItem != 0) {
                currentFocusItem = 0
                onFocusItemIndex.invoke(currentFocusItem)
            } else if (it > 500 && (currentFocusItem != listState.firstVisibleItemIndex + 1)) {
                currentFocusItem = listState.firstVisibleItemIndex + 1
                onFocusItemIndex.invoke(currentFocusItem)
            }
        }
    }
}

@Composable
private fun HandleBack(listState: LazyListState, onBackToTop: Boolean) {
    var backPressHandled by remember { mutableStateOf(false) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val coroutineScope = rememberCoroutineScope()
    if (onBackToTop) {
        BackHandler(enabled = !backPressHandled) {
            if (listState.firstVisibleItemIndex != 0) {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            } else {
                backPressHandled = true
                coroutineScope.launch {
                    awaitFrame()
                    onBackPressedDispatcher?.onBackPressed()
                    backPressHandled = false
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    FeedScreen(/*Preview*/
        uiState = FeedUiState.Loading)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenEmptyPreview() {
    FeedScreen(/*Preview*/
        uiState = FeedUiState.Empty)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenSuccessPreview() {
    FeedScreen(/*Preview*/
        uiState = FeedUiState.Success(
            list = listOf(
                Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty
            )
        ),
        topAppBar = { FeedTopAppBar() }
    )
}

@Preview
@Composable
fun TransitionPreview(){
    var uiState : FeedUiState by remember { mutableStateOf(FeedUiState.Loading) }

    LaunchedEffect(uiState) {
        delay(1000)
        uiState = FeedUiState.Success(listOf(Feed.Sample,Feed.Sample,Feed.Sample))
    }

    FeedScreen(/*Preview*/
        uiState = uiState)
}

@Preview
@Composable
fun PreviewShowReconnect(){
    FeedScreen(/*Preview*/
        uiState = FeedUiState.Reconnect)
}