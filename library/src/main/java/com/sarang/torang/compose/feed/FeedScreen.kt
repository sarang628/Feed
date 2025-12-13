package com.sarang.torang.compose.feed

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
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
import androidx.compose.ui.platform.testTag
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
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.imageHeight
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * @sample FeedScreenEmptyPreview
 * @sample FeedScreenLoadingPreview
 * @sample FeedScreenSuccessPreview
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FeedScreen(
    modifier            : Modifier               = Modifier,
    feedScreenState     : FeedScreenState        = rememberFeedScreenState(),
    tag                 : String                 = "__FeedScreen",
    loadingUiState      : FeedLoadingUiState     = FeedLoadingUiState.Loading,
    feedUiState         : FeedUiState            = FeedUiState(),
    topAppBar           : @Composable () -> Unit = {},
    onBackToTop         : Boolean                = true,
    scrollEnabled       : Boolean                = true,
    pageScrollable      : Boolean                = true,
    feedCallBack        : FeedCallBack           = FeedCallBack(tag = tag),
    contentWindowInsets : WindowInsets           = ScaffoldDefaults.contentWindowInsets,
) {
    HandleOnFocusIndex(feedScreenState.listState, feedCallBack.onFocusItemIndex)
    if(onBackToTop) HandleBack(feedScreenState.listState)

    Scaffold(modifier       = modifier,
        snackbarHost        = { SnackbarHost(hostState = feedScreenState.snackbarState) },
        topBar              = topAppBar,
        contentWindowInsets = contentWindowInsets
    ) { padding ->
        Box {
            FeedListScreen(
                modifier        = Modifier.padding(padding),
                uiState         = feedUiState,
                feedScreenState = feedScreenState,
                scrollEnabled   = scrollEnabled,
                feedCallBack    = feedCallBack,
                pageScrollable  = pageScrollable,
            )

            AnimatedContent(
                targetState     = loadingUiState,
                transitionSpec  = { fadeIn(tween(800)) with
                                    fadeOut(tween(800)) using
                                    SizeTransform(clip = false) }
            ) { uiState ->
                when (uiState) {
                    FeedLoadingUiState.Loading -> FeedShimmer(
                        modifier = Modifier.testTag("shimmer")
                                           .fillMaxSize()
                                           .padding(padding)
                    )

                    is FeedLoadingUiState.Empty -> RefreshAndBottomDetectionLazyColumn(
                        modifier                    = Modifier.padding(padding),
                        listState                   = feedScreenState.listState,
                        pullToRefreshLayoutState    = feedScreenState.pullToRefreshLayoutState,
                        onRefresh                   = feedCallBack.onRefresh,
                        content                     = { EmptyFeed() },
                        count                       = 1,
                        itemCompose                 = {Box(Modifier.height(1000.dp)
                                                                   .fillMaxWidth()){} }
                    )

                    is FeedLoadingUiState.Success -> {}

                    FeedLoadingUiState.Reconnect ->
                        RefreshAndBottomDetectionLazyColumn(
                            listState = feedScreenState.listState,
                            pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
                            content = {
                                        Box(Modifier.fillMaxSize()
                                                    .padding(padding)){
                                            Button(modifier = Modifier.align(Alignment.Center),
                                                   onClick  = feedCallBack.onConnect) {
                                                Text("connect")
                                            }
                                        }
                                     }
                        )

                    is FeedLoadingUiState.Error -> {

                    }
                }
            }
        }
    }
}

@Composable
fun FeedListScreen(
    modifier        : Modifier          = Modifier,
    uiState         : FeedUiState       = FeedUiState(),
    tag             : String            = "__FeedSuccess",
    scrollEnabled   : Boolean           = true,
    feedScreenState : FeedScreenState   = rememberFeedScreenState(),
    feedCallBack    : FeedCallBack      = FeedCallBack(tag = tag),
    pageScrollable  : Boolean           = true,
) {
    RefreshAndBottomDetectionLazyColumn(
        modifier                 = modifier,
        count                    = uiState.list.size,
        onBottom                 = feedCallBack.onBottom,
        userScrollEnabled        = scrollEnabled,
        pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
        listState                = feedScreenState.listState,
        onRefresh                = feedCallBack.onRefresh
    ) {
        LocalFeedCompose.current.invoke(
            FeedTypeData(
                feed            = uiState.list[it],
                onLike          = feedCallBack.onLike,
                onFavorite      = feedCallBack.onFavorite,
                onVideoClick    = { feedCallBack.onVideoClick.invoke(uiState.list[it].reviewId) },
                pageScrollable  = pageScrollable,
                isLogin         = uiState.isLogin,
                imageHeight     = uiState.imageHeight(
                    density = LocalDensity.current,
                    screenWidthDp = LocalConfiguration.current.screenWidthDp,
                    screenHeightDp = LocalConfiguration.current.screenHeightDp
                )
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
private fun HandleBack(listState: LazyListState) {
    var interceptBackPressHandle by remember { mutableStateOf(true) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = interceptBackPressHandle) {
        if (listState.firstVisibleItemIndex != 0) {
            coroutineScope.launch { listState.animateScrollToItem(0) }
        } else {
            interceptBackPressHandle = false // BackHandler 막아야 상위로 뒤로가기 이벤트를 올릴 수 있음.
            coroutineScope.launch {
                awaitFrame()
                onBackPressedDispatcher?.onBackPressed()
                interceptBackPressHandle = true // 상위로 Back 이벤트 올린 후 다시 내부 BackHandler 이벤트 감지 활성
            }
        }
    }
}

data class FeedCallBack(
    val tag                 : String        = "__FeedCallBack",
    val onRefresh           : () -> Unit    = { Log.i(tag, "onRefresh is not set") },
    val onVideoClick        : (Int) -> Unit = { Log.i(tag, "onVideoClick is not set") },
    val onBottom            : () -> Unit    = { Log.i(tag, "onBottom is not set") },
    val onFocusItemIndex    : (Int) -> Unit = { Log.i(tag, "onFocusItemIndex is not set") },
    val onConnect           : () -> Unit    = { Log.i(tag, "onConnect is not set") },
    val onLike              : (Int) -> Unit = { Log.i(tag, "onLike is not set") },
    val onFavorite          : (Int) -> Unit = { Log.i(tag, "onFavorite is not set") }
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Loading)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenEmptyPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Empty)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenSuccessPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Success,
        topAppBar = { FeedTopAppBar() }
    )
}

@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun TransitionPreview(){
    var uiState : FeedLoadingUiState by remember { mutableStateOf(FeedLoadingUiState.Loading) }

    LaunchedEffect(uiState) {
        delay(1000)
        uiState = FeedLoadingUiState.Success
    }

    FeedScreen(/*Preview*/
        loadingUiState = uiState)
}

@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun PreviewReconnect(){
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Reconnect)
}