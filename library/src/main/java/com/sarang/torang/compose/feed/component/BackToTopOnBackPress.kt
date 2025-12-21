package com.sarang.torang.compose.feed.component

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun BackToTopOnBackPress(listState: LazyListState) {
    var interceptBackPressHandle by remember { mutableStateOf(true) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = interceptBackPressHandle) {
        if (listState.firstVisibleItemIndex != 0) {
            coroutineScope.launch { listState.animateScrollToItem(0) }
        } else {
            interceptBackPressHandle = false // BackHandler 막아야 상위로 뒤로가기 이벤트를 올릴 수 있음.
            coroutineScope.launch {
                awaitFrame()
                onBackPressedDispatcher?.onBackPressed()
                interceptBackPressHandle = true // 상위로 Back 이벤트 올린 후 다시 내부 BackHandler 이벤트 감지 활성
            }
        }
    }
}