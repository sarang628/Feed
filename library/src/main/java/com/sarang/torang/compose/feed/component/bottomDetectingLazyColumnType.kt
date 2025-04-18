package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

typealias bottomDetectingLazyColumnType = @Composable (
    Modifier,
    Int,
    () -> Unit,
    @Composable (Int) -> Unit,
    Boolean,
    Arrangement.Vertical,
    LazyListState,
    @Composable (() -> Unit)?
) -> Unit