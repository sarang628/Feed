package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
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
import com.sarang.torang.compose.feed.component.FeedTopAppBar
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
    onAddReview: (() -> Unit) = {
        Log.w(
            "__FeedScreenForMain",
            "onAddReview is not implemented"
        )
    },
    onAlarm: () -> Unit = { Log.w("__FeedScreenForMain", "onAlarm is not implemented") },
    scrollToTop: Boolean,
    onScrollToTop: () -> Unit,
    shimmerBrush: @Composable (Boolean) -> Brush,
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
    bottomDetectingLazyColumn: @Composable (
        Modifier,
        Int,
        () -> Unit,
        @Composable (Int) -> Unit,
        Boolean,
        Arrangement.Vertical,
        LazyListState,
        @Composable (() -> Unit)?
    ) -> Unit
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
        onAlarm = onAlarm,
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
        },
        bottomDetectingLazyColumn = bottomDetectingLazyColumn
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainFeed(
    uiState: FeedUiState, /* ui state */
    onAddReview: (() -> Unit) = {
        Log.w(
            "__MainFeedScreen",
            "onAddReview is not implemented"
        )
    },
    /* click add review */
    onAlarm: () -> Unit = { Log.w("__MainFeedScreen", "onAlarm is not implemented") },
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
    bottomDetectingLazyColumn: @Composable (
        Modifier,
        Int,
        () -> Unit,
        @Composable (Int) -> Unit,
        Boolean,
        Arrangement.Vertical,
        LazyListState,
        @Composable (() -> Unit)?
    ) -> Unit
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
            FeedTopAppBar(
                onAddReview = onAddReview,
                topAppIcon = topAppIcon,
                scrollBehavior = scrollBehavior,
                onAlarm = onAlarm
            )
        },
        shimmerBrush = shimmerBrush,
        pullToRefreshLayout = pullToRefreshLayout,
        onFocusItemIndex = onFocusItemIndex,
        bottomDetectingLazyColumn = bottomDetectingLazyColumn
    )

}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    MainFeed(/*Preview*/
        uiState = FeedUiState.Success(
            list = listOf(
                Feed(
                    reviewId = 0,
                    restaurantId = 0,
                    userId = 0,
                    name = "1",
                    restaurantName = "2",
                    rating = 0f,
                    profilePictureUrl = "",
                    likeAmount = 0,
                    commentAmount = 0,
                    isLike = false,
                    isFavorite = false,
                    contents = "3",
                    createDate = "4",
                    reviewImages = listOf()
                )
            ),
        ),
        onAddReview = { /*TODO*/ },
        consumeErrorMessage = {},
        onRefresh = {},
        onBottom = {},
        pullToRefreshLayout = { _, _, contents ->
            contents.invoke()
        },
        isRefreshing = false,
        onTop = false,
        consumeOnTop = {},
        feed = { _ -> Text("피드가 있어야 보임") },
        shimmerBrush = { it -> linearGradient() },
        bottomDetectingLazyColumn = { _, _, _, _, _, _, _, _ ->
        }
    )
}