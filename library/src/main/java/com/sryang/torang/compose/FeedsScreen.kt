package com.sryang.torang.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.viewmodels.FeedsViewModel

/**
 * DI 모듈에서 제공하는 FeedScreen을 사용해주세요
 */
@Composable
fun _FeedsScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    feeds: @Composable () -> Unit,
    torangToolbar: @Composable () -> Unit,
    errorComponent: @Composable () -> Unit,
    feedMenuBottomSheetDialog: @Composable (Boolean) -> Unit,
    commentBottomSheetDialog: @Composable (Boolean) -> Unit,
    shareBottomSheetDialog: @Composable (Boolean) -> Unit,
    emptyFeed: @Composable (Boolean) -> Unit,
    networkError: @Composable (Boolean) -> Unit,
    loading: @Composable (Boolean) -> Unit,
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    Box {
        Column {
            // 타이틀과 추가버튼이 있는 툴바
            torangToolbar.invoke()
            errorComponent.invoke()
            Box {
                // 피드와 스와이프 리프레시
                feeds.invoke()
                errorComponent.invoke()
            }
        }
        if (uiState.isExpandMenuBottomSheet)
            feedMenuBottomSheetDialog.invoke(true)
        if (uiState.isExpandCommentBottomSheet)
            commentBottomSheetDialog.invoke(true)
        if (uiState.isShareCommentBottomSheet) {
            shareBottomSheetDialog.invoke(true)
        }
        uiState.error?.let {
            AlertDialog(
                onDismissRequest = { feedsViewModel.removeErrorMsg() },
                confirmButton = {
                    Button(onClick = { feedsViewModel.removeErrorMsg() }) {
                        Text(text = "확인", color = Color.White)
                    }
                },
                title = { Text(text = it) })
        }
    }
}