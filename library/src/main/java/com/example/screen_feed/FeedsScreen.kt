package com.example.screen_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource

// UIState 처리
@Composable
fun FeedsScreen(
    feedsViewModel: FeedsViewModel,
    snackBar: String = "",
    isExpandMenuBottomSheet: Boolean = false,
    isExpandCommentBottomSheet: Boolean = false,
    isShareCommentBottomSheet: Boolean = false,
    onReview: (Int) -> Unit,
    itemFeed: @Composable () -> Unit,
    torangToolbar: @Composable () -> Unit,
    errorComponent: @Composable () -> Unit,
    feedMenuBottomSheetDialog: @Composable (Boolean) -> Unit,
    commentBottomSheetDialog: @Composable (Boolean) -> Unit,
    shareBottomSheetDialog: @Composable (Boolean) -> Unit,
) {
    val snackBarHostState = SnackbarHostState()

    LaunchedEffect(key1 = snackBar, block = {
        if (snackBar != "")
            snackBarHostState.showSnackbar(snackBar, null)
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
                        itemFeed.invoke()
                        errorComponent.invoke()
                    }
                }
                if (isExpandMenuBottomSheet)
                    feedMenuBottomSheetDialog.invoke(true)
                if (isExpandCommentBottomSheet)
                    commentBottomSheetDialog.invoke(true)
                if (isShareCommentBottomSheet) {
                    shareBottomSheetDialog.invoke(true)
                }
            }
        }
    }
}