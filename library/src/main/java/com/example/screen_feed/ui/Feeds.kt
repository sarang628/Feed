package com.example.screen_feed.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.data.Feed
import com.example.screen_feed.uistate.FeedUiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Feeds(
    feeds: List<Feed>?,
    isRefreshing: Boolean? = null,
    onRefresh: (() -> Unit)? = null,
    onProfile: ((Int) -> Unit)? = null,
    onImage: ((Int) -> Unit)? = null,
    onMenu: ((Int) -> Unit)? = null,
    onName: ((Int) -> Unit)? = null,
    onRestaurant: ((Int) -> Unit)? = null,
    onLike: ((Int) -> Unit)? = null,
    onComment: ((Int) -> Unit)? = null,
    onShare: ((Int) -> Unit)? = null,
    onFavorite: ((Int) -> Unit)? = null
) {

    val pullRefreshState = rememberPullRefreshState(isRefreshing ?: false, onRefresh ?: { })
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
            LazyColumn {
                items(feeds.size) {
                    Feed(
                        feeds[it].FeedUiState(),
                        onProfile = onProfile,
                        onLike = onLike,
                        onComment = onComment,
                        onShare = onShare,
                        onFavorite = onFavorite,
                        onMenu = onMenu,
                        onName = onName,
                        onRestaurant = onRestaurant,
                        onImage = onImage
                    )
                }
            }

        PullRefreshIndicator(
            refreshing = isRefreshing ?: false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
fun PreviewFeeds() {
    val list = JsonToObjectGenerator<Feed>().getListByFile(
        LocalContext.current,
        "feeds.json",
        Feed::class.java
    )
    Feeds(feeds = list)
}