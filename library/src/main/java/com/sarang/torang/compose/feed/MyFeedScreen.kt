package com.sarang.torang.compose.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.MyFeedsViewModel

@Composable
fun MyFeedScreen(
    feedsViewModel: MyFeedsViewModel = hiltViewModel(),
    onAddReview: (() -> Unit),
    feeds: @Composable (
        /*base feed와 의존성 제거를 위해 함수 밖에서 호출*/
        feedUiState: FeedUiState,
        onRefresh: (() -> Unit),/*base feed 에서 제공*/
        onBottom: (() -> Unit),/*base feed 에서 제공*/
        isRefreshing: Boolean,/*base feed 에서 제공*/
    ) -> Unit
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    val isRefreshing: Boolean by feedsViewModel.isRefreshing.collectAsState()

    feedsViewModel.initialize()

    FeedScreen(
        uiState = uiState,
        onAddReview = onAddReview,
        feeds = {
            feeds.invoke(
                uiState,
                { feedsViewModel.refreshFeed() },
                { feedsViewModel.onBottom() },
                isRefreshing,
            )
        },
        consumeErrorMessage = {
            feedsViewModel.clearErrorMsg()
        }
    )
}