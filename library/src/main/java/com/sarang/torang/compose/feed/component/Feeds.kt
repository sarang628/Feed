package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedsUiState

@Composable
fun Feeds(
    onRefresh: (() -> Unit),
    onBottom: () -> Unit,
    isRefreshing: Boolean,
    feedsUiState: FeedsUiState,
    listState: LazyListState,
    scrollEnabled: Boolean = true,
    feed: @Composable ((Feed) -> Unit)? = null,
) {
    when (feedsUiState) {
        is FeedsUiState.Loading -> {
            FeedShimmer()
        }

        is FeedsUiState.Empty -> {
            RefreshAndBottomDetectionLazyColunm(
                // pull to refresh와 하단 감지 적용 LazyColunm
                count = 0,
                onBottom = {},
                itemCompose = {},
                onRefresh = onRefresh,
                isRefreshing = isRefreshing,
                listState = listState,
                userScrollEnabled = scrollEnabled
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
                    feed?.invoke(feedsUiState.reviews[it])
                },
                onRefresh = onRefresh,
                isRefreshing = isRefreshing,
                userScrollEnabled = scrollEnabled,
                listState = listState
            )
        }

        is FeedsUiState.Error -> {}
    }
}

@Preview
@Composable
fun PreviewFeeds() {
    Feeds(/* Preview */
        onRefresh = { /*TODO*/ },
        onBottom = { /*TODO*/ },
        isRefreshing = false,
        feed = {},
        listState = rememberLazyListState(),
        //feedsUiState = FeedsUiState.Loading
        feedsUiState = FeedsUiState.Success(ArrayList<Feed>().apply {
        })
    )
}