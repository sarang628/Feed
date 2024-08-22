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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.FeedsUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedScreen(
    uiState: FeedUiState, /* ui state */
    consumeErrorMessage: () -> Unit, /* consume error message */
    topAppBar: @Composable () -> Unit,
    feed: @Composable ((
        feed: Feed,
        onLike: (Int) -> Unit,
        onFavorite: (Int) -> Unit,
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
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    // snackbar process
    LaunchedEffect(key1 = uiState, block = {
        if (uiState is FeedUiState.Error) {
            uiState.msg?.let {
                snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                consumeErrorMessage.invoke()
            }
        }
    })

    if (onBackToTop) {
        BackHandler {
            if (listState.firstVisibleItemIndex != 0) {
                coroutine.launch {
                    listState.animateScrollToItem(0)
                }
            } else {
                onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    LaunchedEffect(key1 = onTop) {
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
