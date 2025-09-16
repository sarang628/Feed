package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.component.PullToRefreshLayoutState
import com.sarang.torang.compose.feed.component.RefreshIndicatorState

typealias pullToRefreshLayoutType = @Composable (modifier : Modifier, pullToRefreshLayoutState: PullToRefreshLayoutState, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit


val LocalPullToRefreshLayoutType = compositionLocalOf<pullToRefreshLayoutType> {
    @Composable {modifier,_,_,contents-> Log.w("__LocalPullToRefreshLayoutType", "pullToRefreshLayout is not set");
        Box(modifier = modifier) {
            contents.invoke()
        }
    }
}