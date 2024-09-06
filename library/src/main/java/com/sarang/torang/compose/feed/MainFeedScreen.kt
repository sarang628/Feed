package com.sarang.torang.compose.feed

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.adjustHeight
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedsViewModel

/**
 * 메인화면용 FeedScreen
 * 피드 가져오기, 리프레시, 좋아요, 즐겨찾기 기능 담당
 * 피드 프로필, 코멘트, 메뉴 등은 피드 컴포저블을 통해 상위 컴포저블에서 처리
 * @param feedsViewModel 피드 뷰모델
 * @param feed 피드 컴포저블 (제공받아야 함)
 * @param onAddReview 피드 추가 리뷰
 * @param scrollToTop 피드 스크롤 탑
 * @param onScrollToTop 스크롤 탑 콜백 (이 콜백을 받으면 scrollToTop을 false로 바꿔줘야 함.)
 */
@Composable
fun FeedScreenForMain(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    feed: @Composable ((
        feed: Feed,
        onLike: (Int) -> Unit,
        onFavorite: (Int) -> Unit,
        isLogin: Boolean,
        onVideoClick: () -> Unit,
        imageHeight: Int,
    ) -> Unit
    ),
    onAddReview: (() -> Unit),
    scrollToTop: Boolean,
    onScrollToTop: () -> Unit,
    shimmerBrush: @Composable (Boolean) -> Brush,
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
) {
    val uiState: FeedUiState = feedsViewModel.uiState
    val isRefreshing: Boolean = feedsViewModel.isRefreshing
    val isLogin by feedsViewModel.isLogin.collectAsState(initial = false)
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current

    feedsViewModel.initialize()

    MainFeed(
        uiState = uiState,
        onAddReview = onAddReview,
        isRefreshing = isRefreshing,
        onBottom = { feedsViewModel.onBottom() },
        onRefresh = { feedsViewModel.refreshFeed() },
        consumeErrorMessage = {
            feedsViewModel.clearErrorMsg()
        },
        feed = { it ->
            feed(
                it.copy(
                    isPlaying = it.isPlaying
                            && if (uiState is FeedUiState.Success) {
                        uiState.focusedIndex == uiState.list.indexOf(it)
                    } else false
                ),
                { if (isLogin) feedsViewModel.onLike(it) },
                { if (isLogin) feedsViewModel.onFavorite(it) },
                isLogin,
                { feedsViewModel.onVideoClick(it.reviewId) },
                it.reviewImages[0].adjustHeight(density, screenWidthDp, screenHeightDp)
            )
        },
        onTop = scrollToTop,
        consumeOnTop = onScrollToTop,
        shimmerBrush = shimmerBrush,
        pullToRefreshLayout = pullToRefreshLayout,
        onFocusItemIndex = {
            feedsViewModel.onFocusItemIndex(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainFeed(
    uiState: FeedUiState, /* ui state */
    onAddReview: (() -> Unit), /* click add review */
    consumeErrorMessage: () -> Unit, /* consume error message */
    onBottom: () -> Unit,
    feed: @Composable ((
        feed: Feed,
    ) -> Unit),
    onRefresh: (() -> Unit),
    isRefreshing: Boolean,
    onTop: Boolean,
    consumeOnTop: () -> Unit,
    shimmerBrush: @Composable (Boolean) -> Brush,
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
    onFocusItemIndex: (Int) -> Unit = {},
    topAppIcon: ImageVector = Icons.AutoMirrored.Default.Send,
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val interactionSource = remember { MutableInteractionSource() }
    FeedScreen(
        uiState = uiState,
        consumeErrorMessage = consumeErrorMessage,
        onBottom = onBottom,
        isRefreshing = isRefreshing,
        feed = feed,
        onRefresh = onRefresh,
        onTop = onTop,
        scrollBehavior = scrollBehavior,
        consumeOnTop = consumeOnTop,
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { onAddReview.invoke() }) {
                        Icon(
                            imageVector = topAppIcon,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        shimmerBrush = shimmerBrush,
        pullToRefreshLayout = pullToRefreshLayout,
        onFocusItemIndex = onFocusItemIndex
    )

}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    MainFeed(/*Preview*/
        uiState = FeedUiState.Loading,
        onAddReview = { /*TODO*/ },
        consumeErrorMessage = {},
        onRefresh = {},
        onBottom = {},
        isRefreshing = false,
        onTop = false,
        consumeOnTop = {},
        feed = { _ -> },
        shimmerBrush = { it -> linearGradient() }
    )
}