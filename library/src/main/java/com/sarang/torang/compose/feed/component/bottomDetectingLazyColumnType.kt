package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

typealias bottomDetectingLazyColumnType = @Composable (
    modifier: Modifier,
    count: Int,
    onBottom: () -> Unit,
    itemCompose: @Composable (Int) -> Unit,
    userScrollEnabled: Boolean,
    Arrangement.Vertical,
    listState: LazyListState,
    contents: @Composable (() -> Unit)?
) -> Unit