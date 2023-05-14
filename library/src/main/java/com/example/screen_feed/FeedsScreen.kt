package com.example.screen_feed

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
import androidx.compose.ui.res.colorResource
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.FeedWithRefresh
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.NetworkError
import com.example.screen_feed.ui.TorangToolbar
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.example.screen_feed.uistate.isVisibleRefreshButton

// UIState 처리
@Composable
private fun FeedsScreen(
    uiState: FeedsScreenUiState,
    onRefresh: (() -> Unit)? = null, // 스와이프 리프레시 이벤트
    clickProfile: ((Int) -> Unit)? = null, // 프로필 이미지 클릭
    clickRestaurant: ((Int) -> Unit)? = null, // 식당명 클릭
    clickImage: ((Int) -> Unit)? = null, // 이미지 클릭
    clickMenu: ((Int) -> Unit)? = null, // 피드 메뉴 클릭
    clickName: ((Int) -> Unit)? = null, // 이름 클릭
    clickAddReview: ((Int) -> Unit)? = null, // 리뷰 추가 클릭
    clickLike: ((Int) -> Unit)? = null, // 좋아요 클릭
    clickComment: ((Int) -> Unit)? = null, // 코멘트 클릭
    clickShare: ((Int) -> Unit)? = null, // 공유 클릭
    clickFavority: ((Int) -> Unit)? = null // 즐겨찾기 클릭
) {
    Box() {
        Column(
            Modifier.background(colorResource(id = R.color.colorSecondaryLight))
        ) {
            // 타이틀과 추가버튼이 있는 툴바
            TorangToolbar(clickAddReview = {
                clickAddReview?.invoke(0)
            })
            Box {
                // 피드와 스와이프 리프레시
                FeedWithRefresh(
                    feeds = uiState.feeds,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = onRefresh,
                    clickProfile = clickProfile,
                    clickRestaurant = clickRestaurant,
                    onMenuClickListener = clickMenu,
                    clickImage = clickImage,
                    onNameClickListener = clickName,
                    onLikeClickListener = clickLike,
                    onCommentClickListener = clickComment,
                    onShareClickListener = clickShare,
                    onClickFavoriteListener = clickFavority
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
                    Text(text = uiState.snackBar)
                }
        }
    }
}

@Composable
fun FeedsScreen(
    feedsViewModel: FeedsViewModel,
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
    val ss by feedsViewModel.uiState.collectAsState()

    FeedsScreen(
        uiState = ss,
        onRefresh = onRefresh,
        clickName = clickName,
        clickMenu = clickMenu,
        clickRestaurant = clickRestaurant,
        clickImage = clickImage,
        clickProfile = clickProfile,
        clickAddReview = clickAddReview,
        clickShare = clickShare,
        clickComment = clickComment,
        clickLike = clickLike,
        clickFavority = clickFavority
    )
}