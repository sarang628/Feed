package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.feed.state.PullToRefreshLayoutState
import com.sarang.torang.compose.feed.state.rememberPullToRefreshState
import com.sarang.torang.compose.feed.type.BottomDetectingLazyColumnData
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.PullToRefreshLayoutData

@Composable
internal fun RefreshAndBottomDetectionLazyColumn(
    modifier                 : Modifier = Modifier,
    count                    : Int = 0,
    pullToRefreshLayoutState : PullToRefreshLayoutState = rememberPullToRefreshState(),
    onRefresh                : () -> Unit = {},
    onBottom                 : () -> Unit = {},
    userScrollEnabled        : Boolean = true,
    listState                : LazyListState = rememberLazyListState(),
    listContent              : LazyListScope.() -> Unit  = {},
    content                  : @Composable () -> Unit = {},
    itemCompose              : @Composable (Int) -> Unit = {},
) {
    LocalPullToRefreshLayoutType.current.invoke(
        PullToRefreshLayoutData(modifier = modifier,
                                     pullToRefreshLayoutState = pullToRefreshLayoutState,
                                     onRefresh = onRefresh) {
        LocalBottomDetectingLazyColumnType.current.invoke(
            BottomDetectingLazyColumnData(
                count               = count,
                onBottom            = onBottom,
                itemCompose         = itemCompose,
                userScrollEnabled   = userScrollEnabled,
                arrangement         = Arrangement.spacedBy(10.dp),
                listState           = listState,
                listContent         = listContent,
                content             = content))
    })
}

@Preview
@Composable
fun PreviewRefreshAndBottomDetectionLazyColunm() {
    RefreshAndBottomDetectionLazyColumn(
        modifier = Modifier.fillMaxSize(),
        count = 10,
        content = { Box(modifier = Modifier.fillMaxSize()) { Text("PreviewRefreshAndBottomDetectionLazyColunm") } },
        listState = rememberLazyListState(),
    ){ Text("111") }
}