package com.sarang.torang

import androidx.compose.runtime.Composable
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
private fun FeedScreenByRestaurantId(restaurantId: Int) {
    val state = rememberPullToRefreshState()
    com.sarang.torang.compose.feed.FeedScreenByRestaurantId(
        restaurantId = restaurantId,
        shimmerBrush = { shimmerBrush(it) },
        feed = { it, onLike, onFavorite, isLogin ->
            Feed(
                review = it.toReview(),
                imageLoadCompose = provideTorangAsyncImage(),
                onMenu = {},
                onLike = { onLike.invoke(it.reviewId) },
                onFavorite = { onFavorite.invoke(it.reviewId) },
                onComment = {},
                onShare = {},
                onProfile = {},
                isZooming = {},
                onName = {},
                onImage = {},
                onRestaurant = {},
                onLikes = {},
                isLogin = isLogin,
                expandableText = provideExpandableText()
            )
        },
        pullToRefreshLayout = { isRefreshing, onRefresh, contents ->

            if (isRefreshing) {
                state.updateState(RefreshIndicatorState.Refreshing)
            } else {
                state.updateState(RefreshIndicatorState.Default)
            }

            PullToRefreshLayout(
                pullRefreshLayoutState = state,
                refreshThreshold = 80,
                onRefresh = onRefresh
            ) {
                contents.invoke()
            }
        }
    )
}