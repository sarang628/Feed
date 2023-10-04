package com.example.screen_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import kotlinx.coroutines.launch

/**
 * DI 모듈에서 제공하는 FeedScreen을 사용해주세요
 */
@Composable
fun _FeedsScreen(
    feedsViewModel: FeedsViewModel,
    onReview: (Int) -> Unit,
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
    val uiState by feedsViewModel.uiState.collectAsState()
    val snackBarHostState = SnackbarHostState()
    LaunchedEffect(key1 = uiState.isFailedLoadFeed, block = {
        if (uiState.isFailedLoadFeed) {
            snackBarHostState.showSnackbar("피드를 불러오는데 실패하였습니다.", null)
            feedsViewModel.consumeFailedLoadFeedSnackBar()
        }

    })

    Box {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { contentPadding ->
            contentPadding.calculateTopPadding()
            Box {
                Column(
                    Modifier.background(colorResource(id = R.color.colorSecondaryLight))
                ) {
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
            }
        }
    }
}