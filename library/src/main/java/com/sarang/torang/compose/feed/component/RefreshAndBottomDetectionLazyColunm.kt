package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.pullToRefreshLayoutType

@Composable
internal fun RefreshAndBottomDetectionLazyColumn(
    modifier: Modifier = Modifier,
    count: Int,
    isRefreshing: Boolean,
    onRefresh: (() -> Unit),
    onBottom: () -> Unit,
    userScrollEnabled: Boolean = true,
    listState: LazyListState = rememberLazyListState(),
    itemCompose: @Composable (Int) -> Unit,
    pullToRefreshLayout: pullToRefreshLayoutType = { _, _, _ ->

    },
    bottomDetectingLazyColumn: bottomDetectingLazyColumnType,
    contents: @Composable (() -> Unit)? = null,
) {
    if (pullToRefreshLayout == null) {
        Log.e(
            "__RefreshAndBottomDetectionLazyColunm",
            "it does not show feed.(pullToRefreshLayout is null)"
        )
        return
    }

    pullToRefreshLayout.invoke(isRefreshing, onRefresh) {
        bottomDetectingLazyColumn.invoke(
            modifier,
            count,
            onBottom,
            itemCompose,
            userScrollEnabled,
            Arrangement.spacedBy(10.dp),
            listState,
            contents
        )
    }
}

@Preview
@Composable
fun PreviewRefreshAndBottomDetectionLazyColunm() {
    RefreshAndBottomDetectionLazyColumn(
        modifier = Modifier.fillMaxSize(),
        count = 10,
        onBottom = {},
        itemCompose = {
            Text("111")
        },
        isRefreshing = false,
        onRefresh = {},
        pullToRefreshLayout = { _, _, contents ->
            contents.invoke()
        },
        listState = rememberLazyListState(),
        bottomDetectingLazyColumn = { _, _, _, _, _, _, _, _ -> }
    )
}