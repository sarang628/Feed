package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.LocalPullToRefreshLayoutType

@Composable
internal fun RefreshAndBottomDetectionLazyColumn(
    modifier                 : Modifier = Modifier,
    count                    : Int = 0,
    pullToRefreshLayoutState : PullToRefreshLayoutState = rememberPullToRefreshState(),
    onRefresh                : () -> Unit = {},
    onBottom                 : () -> Unit = {},
    userScrollEnabled        : Boolean = true,
    listState                : LazyListState = rememberLazyListState(),
    contents                 : @Composable () -> Unit = {},
    itemCompose              : @Composable (Int) -> Unit = {},
) {
    LocalPullToRefreshLayoutType.current.invoke(modifier, pullToRefreshLayoutState, onRefresh) {
        LocalBottomDetectingLazyColumnType.current.invoke(Modifier, count, onBottom, itemCompose, userScrollEnabled, Arrangement.spacedBy(10.dp), listState, contents)
    }
}

@Preview
@Composable
fun PreviewRefreshAndBottomDetectionLazyColunm() {
    RefreshAndBottomDetectionLazyColumn(
        modifier = Modifier.fillMaxSize(),
        count = 10,
        contents = { Box(modifier = Modifier.fillMaxSize()) { Text("PreviewRefreshAndBottomDetectionLazyColunm") } },
        listState = rememberLazyListState(),
    ){ Text("111") }
}