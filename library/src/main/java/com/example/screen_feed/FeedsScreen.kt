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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.NetworkError
import com.example.screen_feed.ui.TorangToolbar
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.uistate.FeedsScreenUiState
import com.sarang.base_feed.uistate.isVisibleRefreshButton
import com.sryang.library.CommentBottomSheetDialog
import com.sryang.library.FeedMenuBottomSheetDialog
import com.sryang.library.ShareBottomSheetDialog
import kotlinx.coroutines.flow.StateFlow

// UIState 처리
@Composable
fun FeedsScreen(
    uiStateFlow: StateFlow<FeedsScreenUiState>,
    inputEvents: FeedsScreenInputEvents,
    onBottom: ((Void?) -> Unit),
    snackBar: String = "",
    imageServerUrl: String = "",
    profileImageServerUrl: String,
    isExpandMenuBottomSheet: Boolean = false,
    isExpandCommentBottomSheet: Boolean = false,
    isShareCommentBottomSheet: Boolean = false
) {
    val uiState by uiStateFlow.collectAsState()

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
                    TorangToolbar(clickAddReview = {
                        inputEvents.onAddReview.invoke(0)
                    })
                    Box {
                        // 피드와 스와이프 리프레시
                        Feeds(
                            feeds = uiState.feeds ?: ArrayList(),
                            isRefreshing = uiState.isRefreshing,
                            onRefresh = inputEvents.onRefresh,
                            onProfile = inputEvents.onProfile,
                            onMenu = inputEvents.onMenu,
                            onImage = inputEvents.onImage,
                            onName = inputEvents.onName,
                            onLike = inputEvents.onLike,
                            onComment = inputEvents.onComment,
                            onShare = inputEvents.onShare,
                            onFavorite = inputEvents.onFavorite,
                            onRestaurant = inputEvents.onRestaurant,
                            onBottom = onBottom,
                            imageServerUrl = imageServerUrl,
                            profileImageServerUrl = profileImageServerUrl
                        )

                        Column {
                            if (uiState.isEmptyFeed) {
                                // 피드가 비어있을 때
                                EmptyFeed()
                            }

                            if (uiState.isVisibleRefreshButton()) {
                                // 네트워크 에러
                                NetworkError()
                            }
                            if (uiState.isProgess) {
                                // 로딩
                                Loading()
                            }
                        }
                    }
                }
                if (isExpandMenuBottomSheet)
                    FeedMenuBottomSheetDialog(isExpand = true, onSelect = {})
                if (isExpandCommentBottomSheet)
                    CommentBottomSheetDialog(isExpand = true, onSelect = {})
                if (isShareCommentBottomSheet) {
                    ShareBottomSheetDialog(isExpand = true, onSelect = {})
                }
            }
        }
    }
}