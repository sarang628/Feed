package com.sarang.torang.compose.feed

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.baseFeedLog
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.MyFeedsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFeedScreen(
    feedsViewModel: MyFeedsViewModel = hiltViewModel(),
    reviewId: Int,
    onAddReview: (() -> Unit),
    onBack: (() -> Unit)? = null,
    scrollState: LazyListState,
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

    LaunchedEffect(key1 = reviewId) {
        feedsViewModel.getUserFeed(reviewId)
    }

    LaunchedEffect(key1 = uiState is FeedUiState.Success) {
        val position = feedsViewModel.findIndexByReviewId(reviewId)
        baseFeedLog("position = ${position}")
        delay(100)
        baseFeedLog("scrollState = ${position}")
        scrollState.scrollToItem(position)
    }

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
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Post", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                })
        },
        consumeErrorMessage = {
            feedsViewModel.clearErrorMsg()
        }
    )
}