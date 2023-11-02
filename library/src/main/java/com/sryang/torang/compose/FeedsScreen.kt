package com.sryang.torang.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.viewmodels.FeedsViewModel

data class FeedsScreenInputEvents(
    val onRefresh: (() -> Unit), // 스와이프 리프레시 이벤트
    val onProfile: ((Int) -> Unit), // 프로필 이미지 클릭
    val onRestaurant: ((Int) -> Unit), // 식당명 클릭
    val onImage: ((Int) -> Unit), // 이미지 클릭
    val onMenu: (() -> Unit), // 피드 메뉴 클릭
    val onName: (() -> Unit), // 이름 클릭
    val onAddReview: ((Int) -> Unit), // 리뷰 추가 클릭
    val onLike: ((Int) -> Unit), // 좋아요 클릭
    val onComment: ((Int) -> Unit), // 코멘트 클릭
    val onShare: ((Int) -> Unit), // 공유 클릭
    val onFavorite: ((Int) -> Unit) // 즐겨찾기 클릭
)


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
    }
}