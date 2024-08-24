package com.sarang.torang

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@Composable
fun FeedScreenForMain() {
    val state = rememberPullToRefreshState()
    var onTop by remember { mutableStateOf(false) }
    com.sarang.torang.compose.feed.FeedScreenForMain(
        onAddReview = {},
        onTop = onTop,
        shimmerBrush = { shimmerBrush(it) },
        consumeOnTop = { onTop = false },
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
        },
        onLogin = {
            Log.d("__MainActivity", "request login by main feed")
        }
    )
}