package com.sarang.torang.compose.feed

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.component.EmptyFeed
import com.sarang.torang.compose.feed.component.FeedShimmer
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.compose.feed.component.RefreshAndBottomDetectionLazyColumn
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.imageHeight
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 피드 화면
 * @param uiState               uiState
 * @param topAppBar             상단 앱 바
 * @param onBottom              하단 터치 이벤트
 * @param onRefresh             피드 갱신 요청
 * @param onBackToTop           back 이벤트 시 리스트를 최상단으로 올리는 이벤트
 * @param onFocusItemIndex      비디오 재생을 위해 항목이 중앙에 있을때 호출되는 콜백
 * @param scrollEnabled         리스트 스크롤 가능 여부
 * @sample FeedScreenEmptyPreview
 * @sample FeedScreenLoadingPreview
 * @sample FeedScreenSuccessPreview
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FeedScreen(
    // @formatter:off
    modifier            :Modifier               = Modifier,
    feedScreenState     :FeedScreenState        = rememberFeedScreenState(),
    tag                 :String                 = "__FeedScreen",
    uiState             :FeedUiState            = FeedUiState.Loading,
    topAppBar           :@Composable () -> Unit = {},
    onBackToTop         :Boolean                = true,
    scrollEnabled       :Boolean                = true,
    pageScrollable      :Boolean                = true,
    isLogin             :Boolean                = false,
    feedCallBack        :FeedCallBack           = FeedCallBack(tag = tag)
    // @formatter:on
) {
    HandleOnFocusIndex(feedScreenState.listState, feedCallBack.onFocusItemIndex)
    HandleBack(feedScreenState.listState, onBackToTop)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = feedScreenState.snackbarState) },
        topBar = topAppBar
    ) {
        Box {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = { fadeIn(tween(800)) with fadeOut(tween(800)) }
            ) { state ->
                when (state) {
                    FeedUiState.Loading -> FeedShimmer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )

                    is FeedUiState.Empty -> RefreshAndBottomDetectionLazyColumn(
                        modifier = Modifier.padding(it),
                        listState = feedScreenState.listState,
                        pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
                        onRefresh = feedCallBack.onRefresh,
                        content = { EmptyFeed() },
                        count = 1,
                        itemCompose = {Box(Modifier.height(1000.dp).fillMaxWidth()){} }
                    )

                    /*is FeedUiState.Empty ->  EmptyFeed()*/

                    is FeedUiState.Success -> FeedSuccess(
                        modifier = modifier.padding(it),
                        feedScreenState = feedScreenState,
                        scrollEnabled = scrollEnabled,
                        uiState = state,
                        feedCallBack = feedCallBack,
                        pageScrollable = pageScrollable,
                        isLogin = isLogin
                    )

                    FeedUiState.Reconnect ->
                        RefreshAndBottomDetectionLazyColumn(
                            listState = feedScreenState.listState,
                            pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
                            content = {
                                Box(Modifier.fillMaxSize()){

                                    Button(
                                        modifier = Modifier.align(Alignment.Center),
                                        onClick = feedCallBack.onConnect
                                    ) {
                                        Text("connect")
                                    }
                                }
                            }
                        )

                    is FeedUiState.Error -> {

                    }
                }
            }
        }
    }
}

@Composable
private fun FeedSuccess(
    modifier: Modifier = Modifier,
    tag: String = "__FeedSuccess",
    uiState: FeedUiState.Success = FeedUiState.Success(listOf()),
    scrollEnabled: Boolean = true,
    feedScreenState: FeedScreenState = rememberFeedScreenState(),
    feedCallBack: FeedCallBack = FeedCallBack(tag = tag),
    pageScrollable: Boolean = true,
    isLogin: Boolean = false
) {
    RefreshAndBottomDetectionLazyColumn(
        modifier = modifier,
        count = uiState.list.size,
        onBottom = feedCallBack.onBottom,
        userScrollEnabled = scrollEnabled,
        pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
        listState = feedScreenState.listState,
        onRefresh = feedCallBack.onRefresh
    ) {
        LocalFeedCompose.current.invoke(
            FeedTypeData(
                feed = uiState.list[it],
                onLike = feedCallBack.onLike,
                onFavorite = feedCallBack.onFavorite,
                isLogin = isLogin,
                onVideoClick = { feedCallBack.onVideoClick.invoke(uiState.list[it].reviewId) },
                imageHeight = uiState.imageHeight(
                    density = LocalDensity.current,
                    screenWidthDp = LocalConfiguration.current.screenWidthDp,
                    screenHeightDp = LocalConfiguration.current.screenHeightDp
                ),
                pageScrollable = pageScrollable
            )
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

data class FeedCallBack(
    val tag: String = "",
    val onRefresh: () -> Unit = { Log.i(tag, "onRefresh is not set") },
    val onLike: (Int) -> Unit = { Log.i(tag, "onLike is not set") },
    val onFavorite: (Int) -> Unit = { Log.i(tag, "onFavorite is not set") },
    val onVideoClick: (Int) -> Unit = { Log.i(tag, "onVideoClick is not set") },
    val onBottom: () -> Unit = { Log.i(tag, "onBottom is not set") },
    val onFocusItemIndex: (Int) -> Unit = { Log.i(tag, "onFocusItemIndex is not set") },
    val onConnect: () -> Unit = { Log.i(tag, "onConnect is not set") }
)

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
fun PreviewReconnect(){
    FeedScreen(/*Preview*/
        uiState = FeedUiState.Reconnect)
}