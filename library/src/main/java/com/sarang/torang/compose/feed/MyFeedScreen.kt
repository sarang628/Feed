package com.sarang.torang.compose.feed

import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.MyFeedsViewModel
import kotlinx.coroutines.delay

/**
 * @param listState - feeds 항목에 같은 listState를 넣어줘야 프로필에서 피드 클릭 시 해당 항목으로 이동함.
 */
@Composable
fun MyFeedScreen(
    feedsViewModel: MyFeedsViewModel = hiltViewModel(),
    reviewId: Int,
    onBack: (() -> Unit)? = null,
    listState: LazyListState,
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
        Log.d("__MyFeedScreen", "position = ${position}")
        delay(10)
        Log.d("__MyFeedScreen", "scrollState = ${position}")
        listState.scrollToItem(position)
    }

    _MyFeedScreen(
        uiState = uiState,
        isRefreshing = isRefreshing,
        onBack = onBack,
        consumeErrorMessage = { feedsViewModel.clearErrorMsg() },
        feeds = feeds,
        onRefresh = { feedsViewModel.refreshFeed() },
        onBottom = { feedsViewModel.onBottom() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun _MyFeedScreen(
    uiState: FeedUiState,
    isRefreshing: Boolean,
    onBack: (() -> Unit)? = null,
    onRefresh: (() -> Unit),/*base feed 에서 제공*/
    onBottom: (() -> Unit),/*base feed 에서 제공*/
    consumeErrorMessage: () -> Unit,
    feeds: @Composable ((
        /*base feed와 의존성 제거를 위해 함수 밖에서 호출*/
        feedUiState: FeedUiState,
        onRefresh: (() -> Unit),/*base feed 에서 제공*/
        onBottom: (() -> Unit),/*base feed 에서 제공*/
        isRefreshing: Boolean,/*base feed 에서 제공*/
    ) -> Unit)? = null
) {
    FeedScreen(
        uiState = uiState,
        feeds = {
            feeds?.invoke(
                uiState,
                onRefresh,
                onBottom,
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
        consumeErrorMessage = consumeErrorMessage
    )
}

@Preview
@Composable
fun PreviewMyFeedScreen() {
    _MyFeedScreen(
        uiState = FeedUiState.Loading,
        isRefreshing = false,
        onRefresh = { /*TODO*/ },
        onBottom = { /*TODO*/ },
        consumeErrorMessage = { /*TODO*/ }
    )
}