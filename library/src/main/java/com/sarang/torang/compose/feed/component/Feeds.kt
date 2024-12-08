package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
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
        modifier = Modifier.fillMaxSize(),
        onRefresh = {},
        onBottom = { /*TODO*/ },
        isRefreshing = false,
        feed = { _ -> Text("피드가 있어야 보임") },
        listState = rememberLazyListState(),
        pullToRefreshLayout = { _, _, contents ->
            contents.invoke()
        },
        feedsUiState = FeedsUiState.Success(
            listOf(
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
            )
        ),
        shimmerBrush = { it -> Brush.linearGradient() }
    )
}