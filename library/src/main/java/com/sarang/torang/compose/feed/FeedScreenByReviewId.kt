package com.sarang.torang.compose.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByReviewIdViewModel

// formatter : off
/**
 * ### 리뷰의 사용자 Feed 리스트
 * @param listState - feeds 항목에 같은 listState를 넣어줘야 프로필에서 피드 클릭 시 해당 항목으로 이동함.
 */
@Composable
fun FeedScreenByReviewId(
    reviewId        : Int,
    tag             : String                            = "__FeedScreenByReviewId",
    showLog         : Boolean                           = false,
    feedsViewModel  : FeedScreenByReviewIdViewModel     = hiltViewModel(),
    feedScreenState : FeedScreenState                   = rememberFeedScreenState(),
    onBack          : () -> Unit                        = { },
    pageScrollable  : Boolean                           = true,
) {
    val screenHeightDp  : Int                   = LocalConfiguration.current.screenHeightDp
    val screenWidthDp   : Int                   = LocalConfiguration.current.screenWidthDp
    val density         : Density               = LocalDensity.current

    LaunchedEffect(key1 = reviewId) { // reviewID가 변경되면 피드 로드 요청
        feedsViewModel.getUserFeedByReviewId(reviewId)
    }

    /*LaunchedEffect(feedsViewModel.msgState) {
        if (feedsViewModel.msgState.isNotEmpty()) {
            feedScreenState.showSnackBar(feedsViewModel.msgState[0])
            feedsViewModel.removeTopErrorMessage()
        }
    }*/

    FeedsByReviewId(
        feedUiState         = feedsViewModel.feedUiState,
        feedLoadUiState     = feedsViewModel.feedLoadUiState,
        onBack              = onBack,
        onRefresh           = { feedsViewModel.refreshFeed() },
        onBottom            = { feedsViewModel.onBottom() },
        feedScreenState     = feedScreenState,
    )
}

// formatter : on

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedsByReviewId(
    feedLoadUiState: FeedLoadingUiState,
    feedUiState         : FeedUiState           = FeedUiState(),
    onBack: (() -> Unit)? = null,
    onRefresh: (() -> Unit),/*base feed 에서 제공*/
    onBottom: (() -> Unit),/*base feed 에서 제공*/
    feedScreenState: FeedScreenState = rememberFeedScreenState(),
) {
    FeedScreen(
        loadingUiState = feedLoadUiState,
        feedScreenState = feedScreenState,
        feedUiState = feedUiState,
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Post", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "") }
                })
        },
        feedCallBack = FeedCallBack(
            onRefresh = onRefresh,
            onBottom = onBottom,
        ),
    )
}

@Preview
@Composable
fun PreviewMyFeedScreen() {
    FeedsByReviewId(
        /*Preview*/
        feedLoadUiState = FeedLoadingUiState.Loading,
        onRefresh = {},
        onBottom = {},
    )
}