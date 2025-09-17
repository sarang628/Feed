package com.sarang.torang.compose.feed.type

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
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

val LocalBottomDetectingLazyColumnType = compositionLocalOf<bottomDetectingLazyColumnType> {
    @Composable { _, count, _, itemCompose, _, _, _, contents ->
        Log.e(
            "__LocalBottomDetectingLazyColumnType",
            "bottomDetectingLazyColumn is not set"
        ); LazyColumn { items(count) { itemCompose.invoke(it) } }; contents?.invoke();
    }
}