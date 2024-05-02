package com.sarang.torang.compose.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.FeedsUiState

@Composable
internal fun FeedScreen(
    uiState: FeedUiState, /* ui state */
    consumeErrorMessage: () -> Unit, /* consume error message */
    topAppBar: @Composable () -> Unit,
    feed: @Composable ((Feed) -> Unit)? = null,
    onBottom: () -> Unit,
    onRefresh: (() -> Unit),
    isRefreshing: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    // snackbar process
    LaunchedEffect(key1 = uiState, block = {
        if (uiState is FeedUiState.Error) {
            uiState.msg?.let {
                snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                consumeErrorMessage.invoke()
            }
        }
    })

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
                feed = { feed?.invoke(it) },
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
