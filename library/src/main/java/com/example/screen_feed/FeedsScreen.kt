package com.example.screen_feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.Feeds
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.NetworkError
import com.example.screen_feed.ui.TorangToolbar
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.example.screen_feed.uistate.isVisibleRefreshButton
import kotlinx.coroutines.flow.StateFlow

// UIState 처리
@Composable
fun FeedsScreen(
    uiStateFlow: StateFlow<FeedsScreenUiState>,
    onRefresh: (() -> Unit)? = null, // 스와이프 리프레시 이벤트
    onProfile: ((Int) -> Unit)? = null, // 프로필 이미지 클릭
    onRestaurant: ((Int) -> Unit)? = null, // 식당명 클릭
    onImage: ((Int) -> Unit)? = null, // 이미지 클릭
    onMenu: ((Int) -> Unit)? = null, // 피드 메뉴 클릭
    onName: ((Int) -> Unit)? = null, // 이름 클릭
    onAddReview: ((Int) -> Unit)? = null, // 리뷰 추가 클릭
    onLike: ((Int) -> Unit)? = null, // 좋아요 클릭
    onComment: ((Int) -> Unit)? = null, // 코멘트 클릭
    onShare: ((Int) -> Unit)? = null, // 공유 클릭
    onFavorite: ((Int) -> Unit)? = null // 즐겨찾기 클릭
) {
    val uiState by uiStateFlow.collectAsState()
    Log.d("FeedsScreen", uiState.toString())
    Box {
        Column(
            Modifier.background(colorResource(id = R.color.colorSecondaryLight))
        ) {
            // 타이틀과 추가버튼이 있는 툴바
            TorangToolbar(clickAddReview = {
                onAddReview?.invoke(0)
            })
            Box {
                // 피드와 스와이프 리프레시
                Feeds(
                    feeds = uiState.feeds,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = onRefresh,
                    onProfile = onProfile,
                    onMenu = onMenu,
                    onImage = onImage,
                    onName = onName,
                    onLike = onLike,
                    onComment = onComment,
                    onShare = onShare,
                    onFavorite = onFavorite,
                    onRestaurant = onRestaurant
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
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            if (uiState.snackBar != null)
                Snackbar {
                    Text(text = uiState.snackBar.toString())
                }
        }
    }
}

@Composable
fun TestFeedsScreen(
    onRefresh: (() -> Unit)? = null,
    clickProfile: ((Int) -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    clickMenu: ((Int) -> Unit)? = null,
    clickName: ((Int) -> Unit)? = null,
    clickAddReview: ((Int) -> Unit)? = null,
    clickLike: ((Int) -> Unit)? = null,
    clickComment: ((Int) -> Unit)? = null,
    clickShare: ((Int) -> Unit)? = null,
    clickFavority: ((Int) -> Unit)? = null
) {
    val feedsViewModel = FeedsViewModel(LocalContext.current)
    FeedsScreen(
        uiStateFlow = feedsViewModel.uiState,
        onRefresh = onRefresh,
        onName = clickName,
        onMenu = clickMenu,
        onRestaurant = clickRestaurant,
        onImage = clickImage,
        onProfile = clickProfile,
        onAddReview = clickAddReview,
        onShare = clickShare,
        onComment = clickComment,
        onLike = {
            clickLike?.invoke(it)
            feedsViewModel.clickLike(it)
        },
        onFavorite = {
            clickFavority?.invoke(it)
            feedsViewModel.clickFavorite(it)
        }
    )
}