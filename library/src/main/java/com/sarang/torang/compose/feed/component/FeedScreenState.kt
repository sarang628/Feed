package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@Composable
fun rememberFeedScreenState(): FeedScreenState {
    val listState: LazyListState = rememberLazyListState()
    val snackbarState: SnackbarHostState = SnackbarHostState()
    return remember { FeedScreenState(listState, snackbarState) }
}

class FeedScreenState(val listState: LazyListState, val snackbarState: SnackbarHostState) {
    suspend fun onTop() {
        listState.animateScrollToItem(0)
    }

    suspend fun showSnackBar(message : String?) {
        message?.let { snackbarState.showSnackbar(it) }
    }
}