package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.adjustHeight
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.MyFeedsViewModel
import kotlinx.coroutines.delay

// formatter : off
/**
 * ### 리뷰ID의 해당 사용자 Feed 리스트 화면
 * @param listState - feeds 항목에 같은 listState를 넣어줘야 프로필에서 피드 클릭 시 해당 항목으로 이동함.
 */
@Composable
fun UserFeedByReviewIdScreen(
    feedsViewModel: MyFeedsViewModel = hiltViewModel(),
    reviewId: Int,
    onBack: (() -> Unit)? = null,
    listState: LazyListState,
    pullToRefreshLayout: pullToRefreshLayoutType,
    pageScrollable: Boolean = true
) {
    val uiState: FeedUiState = feedsViewModel.uiState
    val isRefreshing: Boolean = feedsViewModel.isRefreshing
    val isLogin by feedsViewModel.isLogin.collectAsState(false)
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current

    LaunchedEffect(key1 = reviewId) { // reviewID가 변경되면 피드 로드 요청
        feedsViewModel.getUserFeedByReviewId(reviewId)
    }

    LaunchedEffect(key1 = uiState is FeedUiState.Success) { // 피드 리스트에서 reviewId에 해당 피드로 이동
        val position = feedsViewModel.findIndexByReviewId(reviewId)
        delay(10)
        listState.scrollToItem(position)
    }

    MyFeed(
        uiState = uiState,
        isRefreshing = isRefreshing,
        onBack = onBack,
        consumeErrorMessage = { feedsViewModel.clearErrorMsg() },
        onRefresh = { feedsViewModel.refreshFeed() },
        onBottom = { feedsViewModel.onBottom() },
        feed = { it ->
            LocalFeedCompose.current.invoke(
                it,
                { feedsViewModel.onLike(it) },
                { feedsViewModel.onFavorite(it) },
                isLogin, { feedsViewModel.onVideoClick(it.reviewId) },
                it.reviewImages[0].adjustHeight(density, screenWidthDp, screenHeightDp),
                pageScrollable
            )
        },
        listState = listState,
        pullToRefreshLayout = pullToRefreshLayout,
    )
}

// formatter : on

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyFeed(
    uiState: FeedUiState,
    isRefreshing: Boolean,
    onBack: (() -> Unit)? = null,
    onRefresh: (() -> Unit),/*base feed 에서 제공*/
    onBottom: (() -> Unit),/*base feed 에서 제공*/
    consumeErrorMessage: () -> Unit,
    listState: LazyListState,
    feed: @Composable ((feed: Feed) -> Unit),
    pullToRefreshLayout: pullToRefreshLayoutType = { _, _, _ -> Log.w("__MyFeed", "pullToRefreshLayout is not set") },
) {
    FeedScreen(
        uiState = uiState,
        feed = feed,
        listState = listState,
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
        consumeErrorMessage = consumeErrorMessage,
        onRefresh = onRefresh,
        onBottom = onBottom,
        isRefreshing = isRefreshing,
        onTop = false,
        consumeOnTop = { },
        pullToRefreshLayout = pullToRefreshLayout,
    )
}

@Preview
@Composable
fun PreviewMyFeedScreen() {
    MyFeed(
        /*Preview*/
        uiState = FeedUiState.Loading,
        isRefreshing = false,
        onRefresh = { /*TODO*/ },
        onBottom = { /*TODO*/ },
        consumeErrorMessage = { /*TODO*/ },
        listState = rememberLazyListState(),
        feed = { _ -> },
    )
}