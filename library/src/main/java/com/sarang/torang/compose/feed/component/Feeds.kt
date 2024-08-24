package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedsUiState

@Composable
fun Feeds(
    modifier: Modifier = Modifier,
    onBottom: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: (() -> Unit),
    feedsUiState: FeedsUiState,
    listState: LazyListState,
    scrollEnabled: Boolean = true,
    feed: @Composable ((
        feed: Feed,
    ) -> Unit),
    shimmerBrush: @Composable (Boolean) -> Brush,
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
) {
    when (feedsUiState) {
        is FeedsUiState.Loading -> {
            FeedShimmer(shimmerBrush)
        }

        is FeedsUiState.Empty -> {
            RefreshAndBottomDetectionLazyColunm(
                // pull to refresh와 하단 감지 적용 LazyColunm
                count = 0,
                onBottom = {},
                itemCompose = {},
                isRefreshing = isRefreshing,
                listState = listState,
                userScrollEnabled = scrollEnabled,
                onRefresh = onRefresh
            ) {
                EmptyFeed()
            }
        }

        is FeedsUiState.Success -> {
            RefreshAndBottomDetectionLazyColunm(
                // pull to refresh와 하단 감지 적용 LazyColunm
                count = feedsUiState.reviews.size,
                onBottom = onBottom,
                itemCompose = {
                    feed.invoke(feedsUiState.reviews[it])

                },
                userScrollEnabled = scrollEnabled,
                listState = listState,
                modifier = modifier,
                pullToRefreshLayout = pullToRefreshLayout,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
            )
        }

        is FeedsUiState.Error -> {}
    }
}

@Preview
@Composable
fun PreviewFeeds() {
    Feeds(/* Preview */
        onRefresh = {},
        onBottom = { /*TODO*/ },
        isRefreshing = false,
        feed = { _-> },
        listState = rememberLazyListState(),
        //feedsUiState = FeedsUiState.Loading
        feedsUiState = FeedsUiState.Success(ArrayList<Feed>().apply {
        }),
        shimmerBrush = { it -> Brush.linearGradient() }
    )
}