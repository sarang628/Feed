package com.sarang.torang.compose.feed

import androidx.compose.runtime.Composable

typealias pullToRefreshLayoutType = @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)?