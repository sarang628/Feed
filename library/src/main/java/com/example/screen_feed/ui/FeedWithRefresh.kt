package com.example.screen_feed.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.screen_feed.data.Feed

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedWithRefresh(
    feeds: List<Feed>?,
    isRefreshing: Boolean,
    onRefresh: (() -> Unit)? = null,
    clickProfile: ((Int) -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    onMenuClickListener: ((Int) -> Unit)? = null,
    onNameClickListener: ((Int) -> Unit)? = null,
    onRestaurantClickListener: ((Int) -> Unit)? = null,
    onLikeClickListener: ((Int) -> Unit)? = null,
    onCommentClickListener: ((Int) -> Unit)? = null,
    onShareClickListener: ((Int) -> Unit)? = null,
    onClickFavoriteListener: ((Int) -> Unit)? = null
) {

    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh ?: { })
    val mod1 = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)
        .verticalScroll(rememberScrollState())

    val mod2 = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)

    Box(
        modifier = if (feeds == null) mod1 else mod2
    ) {

        if (feeds != null)
            FeedList(
                clickProfile = clickProfile,
                list = feeds,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                clickRestaurant = clickRestaurant,
                clickImage = clickImage,
                onMenuClickListener = onMenuClickListener,
                onNameClickListener = onNameClickListener,
                onRestaurantClickListener = onRestaurantClickListener,
                onLikeClickListener = onLikeClickListener,
                onCommentClickListener = onCommentClickListener,
                onShareClickListener = onShareClickListener,
                onClickFavoriteListener = onClickFavoriteListener
            )

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}