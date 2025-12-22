package com.sarang.torang.compose.feed.type

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.feed.state.PullToRefreshLayoutState

typealias pullToRefreshLayoutType = @Composable (PullToRefreshLayoutData) -> Unit

data class PullToRefreshLayoutData(
    val modifier                : Modifier = Modifier,
    val pullToRefreshLayoutState: PullToRefreshLayoutState,
    val onRefresh               : () -> Unit = {},
    val contents                : @Composable () -> Unit = {}
)

val LocalPullToRefreshLayoutType = compositionLocalOf<pullToRefreshLayoutType> {
    @Composable { it ->
        Log.w("__LocalPullToRefreshLayoutType", "pullToRefreshLayout is not set");
        Box(modifier = it.modifier) {
            it.contents.invoke()
        }
    }
}