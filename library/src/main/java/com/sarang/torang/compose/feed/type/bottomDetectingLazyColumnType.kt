package com.sarang.torang.compose.feed.type

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

typealias bottomDetectingLazyColumnType = @Composable (
    BottomDetectingLazyColumnData
) -> Unit

data class BottomDetectingLazyColumnData(
    val modifier: Modifier = Modifier,
    val count: Int = 0,
    val onBottom: () -> Unit = {},
    val itemCompose: @Composable (Int) -> Unit = {},
    val userScrollEnabled: Boolean = false,
    val arrangement : Arrangement.Vertical,
    val listState: LazyListState,
    val content: @Composable () -> Unit = {}
)

val LocalBottomDetectingLazyColumnType = compositionLocalOf<bottomDetectingLazyColumnType> {
    @Composable { data ->
        Log.e(
            "__LocalBottomDetectingLazyColumnType",
            "bottomDetectingLazyColumn is not set"
        ); LazyColumn { items(data.count) { data.itemCompose.invoke(it) } }
        data.content.invoke()
    }
}