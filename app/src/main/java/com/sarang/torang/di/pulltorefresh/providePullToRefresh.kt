package com.sarang.torang.di.pulltorefresh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.RefreshIndicatorState

fun providePullToRefresh(state: PullToRefreshLayoutState): @Composable ((modifier : Modifier, state : com.sarang.torang.compose.feed.component.RefreshIndicatorState, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit) =
    { modifier, pullToRefreshLayoutState, onRefresh, contents ->


        state.updateState(
            when(pullToRefreshLayoutState){
                com.sarang.torang.compose.feed.component.RefreshIndicatorState.Default -> RefreshIndicatorState.Default
                com.sarang.torang.compose.feed.component.RefreshIndicatorState.PullingDown -> RefreshIndicatorState.PullingDown
                com.sarang.torang.compose.feed.component.RefreshIndicatorState.ReachedThreshold -> RefreshIndicatorState.ReachedThreshold
                com.sarang.torang.compose.feed.component.RefreshIndicatorState.Refreshing -> RefreshIndicatorState.Refreshing
            })

        PullToRefreshLayout(
            modifier = modifier, pullRefreshLayoutState = state, refreshThreshold = 80, onRefresh = onRefresh
        ) {
            contents.invoke()
        }
    }