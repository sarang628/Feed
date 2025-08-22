package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.adjustHeight
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.imageHeight
import kotlinx.coroutines.android.awaitFrame
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
 * @param listState 리스트 상태 library
 * @param consumeOnTop 최상단 이벤트 소비
 * @param onBackToTop back 이벤트 시 리스트를 최상단으로 올리는 이벤트
 * @param onFocusItemIndex 비디오 재생을 위해 항목이 중앙에 있을때 호출되는 콜백
 * @param scrollEnabled 리스트 스크롤 가능 여부
 *
 * @sample FeedScreenEmptyPreview
 * @sample FeedScreenLoadingPreview
 * @sample FeedScreenSuccessPreview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    // @formatter:off
    modifier:                   Modifier                            = Modifier,
    tag:                        String                              = "__FeedScreen",
    uiState:                    FeedUiState                         = FeedUiState.Loading,
    errorMsg:                   String                              = "",
    listState:                  LazyListState                       = rememberLazyListState(),
    topAppBar:                  @Composable () -> Unit              = { Log.i(tag, "topAppBar is not set") },
    isRefreshing:               Boolean                             = true,
    onTop:                      Boolean                             = false,
    onBackToTop:                Boolean                             = true,
    scrollEnabled:              Boolean                             = true,
    consumeErrorMessage:        () -> Unit                          = { Log.w(tag, "consumeErrorMessage is not set") },
    onBottom:                   () -> Unit                          = { Log.i(tag, "onBottom is not set") },
    onRefresh:                  () -> Unit                          = { Log.i(tag, "onRefresh is not set") },
    consumeOnTop:               () -> Unit                          = { Log.i(tag, "consumeOnTop is not set") },
    onFocusItemIndex:           (Int) -> Unit                       = { Log.i(tag, "onFocusItemIndex is not set") },
    snackBarHostState:          SnackbarHostState                   = remember { SnackbarHostState() },
    onLike :                    (Int) -> Unit                       = {},
    onFavorite:                 (Int) -> Unit                       = {},
    onVideoClick :              (Int) -> Unit                       = {},
    onConnect :                 () -> Unit                          = {},
    pageScrollable:             Boolean                             = true,
    isLogin:                    Boolean                             = false,
    showReConnect:              Boolean                             = false
    // @formatter:on
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current

    Scaffold( // snackbar + topAppBar + feedList
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = topAppBar
    ) {
        Box(Modifier.fillMaxWidth()){
            when (uiState) {
                is FeedUiState.Loading -> { FeedShimmer(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)) }
                is FeedUiState.Empty -> {
                    RefreshAndBottomDetectionLazyColumn(modifier = Modifier.padding(it), count = 0, onBottom = {}, isRefreshing = isRefreshing, listState = listState, userScrollEnabled = scrollEnabled, onRefresh = onRefresh, contents = {EmptyFeed()}) {  }
                }
                is FeedUiState.Success -> {
                    RefreshAndBottomDetectionLazyColumn(modifier = modifier.padding(it), count = uiState.list.size, onBottom = onBottom, userScrollEnabled = scrollEnabled, listState = listState, isRefreshing = isRefreshing, onRefresh = onRefresh) {
                        val feed = uiState.list[it].copy(/*isPlaying = it.isPlaying && if (uiState is FeedUiState.Success) { (uiState as FeedUiState.Success).focusedIndex == (uiState as FeedUiState.Success).list.indexOf(it) } else false*/)
                        var imageHeight = uiState.imageHeight(density, screenWidthDp, screenHeightDp)
                        LocalFeedCompose.current.invoke(feed, onLike, onFavorite, isLogin, { onVideoClick.invoke(uiState.list[it].reviewId) }, imageHeight, pageScrollable)
                    }
                }
                is FeedUiState.Error -> {}
            }
            if(showReConnect) {
                Button(modifier = Modifier.align(Alignment.Center), onClick = onConnect) {
                    Text("connect")
                }
            }
        }
    }

    HandleSnackBar(errorMsg, snackBarHostState, consumeErrorMessage)
    HandleOnFocusIndex(listState, onFocusItemIndex)
    HandleBack(listState, onBackToTop)
    HandleOnTop(listState, onTop, consumeOnTop)
}

@Composable
private fun HandleSnackBar(
    msg: String,
    snackBarHostState: SnackbarHostState,
    consumeErrorMessage: () -> Unit
) {
// snackbar process
    LaunchedEffect(key1 = msg, block = {
        if (msg.isNotEmpty()) {
            snackBarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
            consumeErrorMessage.invoke()
        }
    })
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

@Composable
private fun HandleOnTop(listState: LazyListState, onTop: Boolean, consumeOnTop: () -> Unit) {
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = onTop) {
        if (onTop) {
            consumeOnTop.invoke()
            coroutine.launch { listState.animateScrollToItem(0) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    FeedScreen(uiState = FeedUiState.Loading)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenEmptyPreview() {
    FeedScreen(uiState = FeedUiState.Empty)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenSuccessPreview() {
    FeedScreen(
        uiState = FeedUiState.Success(
            list = listOf(
                Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty
            )
        ),
        topAppBar = { FeedTopAppBar() }
    )
}