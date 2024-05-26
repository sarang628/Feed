package com.sarang.torang.compose.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedsViewModel

/**
 * 메인화면용 FeedScreen
 * 피드 가져오기, 리프레시, 좋아요, 즐겨찾기 기능 담당
 * 피드 프로필, 코멘트, 메뉴 등은 피드 컴포저블을 통해 상위 컴포저블에서 처리
 * @param feedsViewModel 피드 뷰모델
 * @param feed 피드 컴포저블 (제공받아야 함)
 * @param onAddReview 피드 추가 리뷰
 * @param onTop 피드 스크롤 탑
 * @param consumeOnTop 스크롤 탑 컨슈머
 */
@Composable
fun FeedScreenForMain(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    feed: @Composable ((Feed) -> Unit)? = null,
    onAddReview: (() -> Unit),
    onTop: Boolean,
    consumeOnTop: () -> Unit,
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    val isRefreshing: Boolean by feedsViewModel.isRefreshing.collectAsState()

    feedsViewModel.initialize()

    _MainFeedScreen(
        uiState = uiState,
        onAddReview = onAddReview,
        isRefreshing = isRefreshing,
        onBottom = { feedsViewModel.onBottom() },
        onRefresh = { feedsViewModel.refreshFeed() },
        consumeErrorMessage = {
            feedsViewModel.clearErrorMsg()
        },
        feed = feed,
        onTop = onTop,
        consumeOnTop = consumeOnTop
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun _MainFeedScreen(
    uiState: FeedUiState, /* ui state */
    onAddReview: (() -> Unit), /* click add review */
    consumeErrorMessage: () -> Unit, /* consume error message */
    onBottom: () -> Unit,
    feed: @Composable ((Feed) -> Unit)? = null,
    onRefresh: (() -> Unit),
    isRefreshing: Boolean,
    onTop: Boolean,
    consumeOnTop: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    FeedScreen(
        uiState = uiState,
        consumeErrorMessage = consumeErrorMessage,
        onBottom = onBottom,
        isRefreshing = isRefreshing,
        feed = feed,
        onRefresh = onRefresh,
        onTop = onTop,
        consumeOnTop = consumeOnTop,
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    Icon(imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                onAddReview.invoke()
                            })
                })
        }
    )
}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    _MainFeedScreen(/*Preview*/
        /*Preview*/
        uiState = FeedUiState.Loading,
        onAddReview = { /*TODO*/ },
        consumeErrorMessage = {},
        onRefresh = {},
        onBottom = {},
        isRefreshing = false,
        onTop = false,
        consumeOnTop = {}
    )
}