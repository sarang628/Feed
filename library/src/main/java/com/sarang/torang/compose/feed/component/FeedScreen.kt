package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.FeedsUiState
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

private val TAG = "__FeedScreen"

/**
 * @param onFocusItemIndex 비디오 재생을 위해 항목이 중앙에 있을때 호출되는 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedScreen(
    uiState: FeedUiState, /* ui state */
    consumeErrorMessage: () -> Unit, /* consume error message */
    topAppBar: @Composable () -> Unit,
    feed: @Composable ((
        feed: Feed,
    ) -> Unit),
    onBottom: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: (() -> Unit),
    listState: LazyListState = rememberLazyListState(),
    onTop: Boolean,
    consumeOnTop: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    shimmerBrush: @Composable (Boolean) -> Brush,
    onBackToTop: Boolean = true,
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
    onFocusItemIndex: (Int) -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var currentFocusItem by remember { mutableIntStateOf(-1) }
    // snackbar process
    LaunchedEffect(key1 = uiState, block = {
        if (uiState is FeedUiState.Error) {
            Log.d(TAG, "error message : ${uiState.msg}")
            uiState.msg?.let {
                snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                consumeErrorMessage.invoke()
            }
        }
    })

    LaunchedEffect(key1 = listState) {
        snapshotFlow {
            listState.firstVisibleItemScrollOffset
        }.collect {
            if (listState.firstVisibleItemIndex == 0 && it < 500 && currentFocusItem != 0) {
                Log.d(TAG, "currentFocusItem : ${listState.firstVisibleItemIndex} ")
                currentFocusItem = 0
                onFocusItemIndex.invoke(currentFocusItem)
            } else if (it > 500 && (currentFocusItem != listState.firstVisibleItemIndex + 1)) {
                Log.d(TAG, "currentFocusItem : ${listState.firstVisibleItemIndex + 1} ")
                currentFocusItem = listState.firstVisibleItemIndex + 1
                onFocusItemIndex.invoke(currentFocusItem)
            }
        }
    }

    if (onBackToTop) {
        BackHandler(enabled = !backPressHandled) {
            if (listState.firstVisibleItemIndex != 0) {
                coroutine.launch {
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

    LaunchedEffect(key1 = onTop) {
        if (onTop)
            Log.d("__FeedScreen", "received onTop:${onTop}")

        if (onTop) {
            consumeOnTop.invoke()
            coroutine.launch {
                listState.animateScrollToItem(0)
            }
            //listState.scrollToItem(0)
        }
    }

    // snackbar + topAppBar + feedList
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = topAppBar
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        {
            Feeds(
                modifier = if (scrollBehavior != null) {
                    Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                } else Modifier,
                onBottom = onBottom,
                feed = feed,
                listState = listState,
                feedsUiState = when (uiState) {
                    is FeedUiState.Loading -> {
                        FeedsUiState.Loading
                    }

                    is FeedUiState.Error -> {
                        FeedsUiState.Error("")
                    }

                    is FeedUiState.Success -> {
                        FeedsUiState.Success(uiState.list)
                    }
                },
                shimmerBrush = shimmerBrush,
                pullToRefreshLayout = pullToRefreshLayout,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
            )
        }
    }
}
