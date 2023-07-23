package com.example.screen_feed

import android.util.Log
import android.view.InputEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.Feeds
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.NetworkError
import com.example.screen_feed.ui.TorangToolbar
import com.sarang.base_feed.uistate.FeedsScreenUiState
import com.sarang.base_feed.uistate.isVisibleRefreshButton
import com.sryang.torang_repository.data.dao.FeedDao
import kotlinx.coroutines.flow.StateFlow

// UIState 처리
@Composable
fun FeedsScreen(
    uiStateFlow: StateFlow<FeedsScreenUiState>,
    inputEvents: FeedsScreenInputEvents?,
    onBottom: ((Void?) -> Unit)? = null,
    snackBar: String = ""
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
            Column(
                Modifier.background(colorResource(id = R.color.colorSecondaryLight))
            ) {
                // 타이틀과 추가버튼이 있는 툴바
                TorangToolbar(clickAddReview = {
                    inputEvents?.onAddReview?.invoke(0)
                })
                Box {
                    // 피드와 스와이프 리프레시
                    Feeds(
                        feeds = uiState.feeds,
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = inputEvents?.onRefresh,
                        onProfile = inputEvents?.onProfile,
                        onMenu = inputEvents?.onMenu,
                        onImage = inputEvents?.onImage,
                        onName = inputEvents?.onName,
                        onLike = inputEvents?.onLike,
                        onComment = inputEvents?.onComment,
                        onShare = inputEvents?.onShare,
                        onFavorite = inputEvents?.onFavorite,
                        onRestaurant = inputEvents?.onRestaurant,
                        onBottom = onBottom
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
        }
    }
}

@Composable
fun TestFeedsScreen(
    feedsViewModel: FeedsViewModel,
    feedsScreenInputEvents: FeedsScreenInputEvents? = null
) {
    FeedsScreen(
        uiStateFlow = feedsViewModel.uiState,
        inputEvents = feedsScreenInputEvents,
        onBottom = {

        }
    )
}

data class FeedsScreenInputEvents(
    val onRefresh: (() -> Unit)? = null, // 스와이프 리프레시 이벤트
    val onProfile: ((Int) -> Unit)? = null, // 프로필 이미지 클릭
    val onRestaurant: ((Int) -> Unit)? = null, // 식당명 클릭
    val onImage: ((Int) -> Unit)? = null, // 이미지 클릭
    val onMenu: ((Int) -> Unit)? = null, // 피드 메뉴 클릭
    val onName: ((Int) -> Unit)? = null, // 이름 클릭
    val onAddReview: ((Int) -> Unit)? = null, // 리뷰 추가 클릭
    val onLike: ((Int) -> Unit)? = null, // 좋아요 클릭
    val onComment: ((Int) -> Unit)? = null, // 코멘트 클릭
    val onShare: ((Int) -> Unit)? = null, // 공유 클릭
    val onFavorite: ((Int) -> Unit)? = null // 즐겨찾기 클릭
)