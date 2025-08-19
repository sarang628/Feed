package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

typealias pullToRefreshLayoutType = @Composable (modifier : Modifier, isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit


val LocalPullToRefreshLayoutType = compositionLocalOf<pullToRefreshLayoutType> {
    @Composable {_,_,_,contents-> Log.w("__LocalPullToRefreshLayoutType", "pullToRefreshLayout is not set"); contents.invoke() }
}