package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.FeedsUiState
import kotlinx.coroutines.launch

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
    onRefresh: (() -> Unit),
    isRefreshing: Boolean,
    listState: LazyListState = rememberLazyListState(),
    onTop: Boolean,
    consumeOnTop: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    // snackbar process
    LaunchedEffect(key1 = uiState, block = {
        if (uiState is FeedUiState.Error) {
            uiState.msg?.let {
                snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                consumeErrorMessage.invoke()
            }
        }
    })

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
                onRefresh = onRefresh,
                onBottom = onBottom,
                isRefreshing = isRefreshing,
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
                }

            )
        }
    }
}
