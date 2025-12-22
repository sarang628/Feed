package com.sarang.torang.compose.feed.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@Composable
fun rememberFeedScreenState(): FeedScreenState {
    val listState           : LazyListState             = rememberLazyListState()
    val snackHostBarState   : SnackbarHostState         = SnackbarHostState()
    val pullToRefreshState  : PullToRefreshLayoutState  = rememberPullToRefreshState()
    return remember { FeedScreenState(listState                = listState,
                                      snackBarState            = snackHostBarState,
                                      pullToRefreshLayoutState = pullToRefreshState) }
}

class FeedScreenState(val listState                 : LazyListState,
                      val snackBarState             : SnackbarHostState,
                      val pullToRefreshLayoutState  : PullToRefreshLayoutState) {
    suspend fun onTop() {
        listState.animateScrollToItem(0)
    }

    suspend fun showSnackBar(message : String?) {
        message?.let { snackBarState.showSnackbar(it) }
    }

    fun refresh(refresh : Boolean){
        pullToRefreshLayoutState.updateState(refreshState = if(!refresh) RefreshIndicatorState.Default
                                                            else RefreshIndicatorState.Refreshing)
    }
}