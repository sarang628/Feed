package com.sarang.torang.compose.feed.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.sarang.torang.compose.feed.state.RefreshIndicatorState


@Composable
fun rememberFeedScreenState(): FeedScreenState {
    val listState: LazyListState = rememberLazyListState()
    val snackbarState: SnackbarHostState = SnackbarHostState()
    val pullToRefreshState = rememberPullToRefreshState()
    return remember { FeedScreenState(listState, snackbarState, pullToRefreshState) }
}

class FeedScreenState(val listState                 : LazyListState,
                      val snackbarState             : SnackbarHostState,
                      val pullToRefreshLayoutState  : PullToRefreshLayoutState) {
    suspend fun onTop() {
        listState.animateScrollToItem(0)
    }

    suspend fun showSnackBar(message : String?) {
        message?.let { snackbarState.showSnackbar(it) }
    }

    fun refresh(refresh : Boolean){
        pullToRefreshLayoutState.updateState(if(!refresh) RefreshIndicatorState.Default else RefreshIndicatorState.Refreshing)
    }
}