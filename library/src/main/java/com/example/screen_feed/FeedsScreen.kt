package com.example.screen_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.screen_feed.data.Feed
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.FeedList
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.RefreshButton
import com.example.screen_feed.ui.TorangToolbar
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.example.screen_feed.uistate.isVisibleRefreshButton

// UIState 처리
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FeedsScreen(
    uiState: FeedsScreenUiState,
    onRefresh: (() -> Unit)? = null,
    clickProfile: ((Int) -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    onMenuClickListener: ((Int) -> Unit)? = null,
    onNameClickListener: ((Int) -> Unit)? = null,
    onRestaurantClickListener: ((Int) -> Unit)? = null,
    clickAddReview: ((Int) -> Unit)? = null,
    onLikeClickListener: ((Int) -> Unit)? = null,
    onCommentClickListener: ((Int) -> Unit)? = null,
    onShareClickListener: ((Int) -> Unit)? = null,
    onClickFavoriteListener: ((Int) -> Unit)? = null
) {
    Box() {
        Column(
            Modifier.background(colorResource(id = R.color.colorSecondaryLight))
        ) {
            TorangToolbar(clickAddReview = {
                clickAddReview?.invoke(0)
            })
            Box {

                FeedWithRefresh(
                    feeds = uiState.feeds,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = onRefresh,
                    clickProfile = clickProfile,
                    clickRestaurant = clickRestaurant,
                    onMenuClickListener = onMenuClickListener,
                    onRestaurantClickListener = onRestaurantClickListener,
                    clickImage = clickImage,
                    onNameClickListener = onNameClickListener,
                    onLikeClickListener = onLikeClickListener,
                    onCommentClickListener = onCommentClickListener,
                    onShareClickListener = onShareClickListener,
                    onClickFavoriteListener = onClickFavoriteListener
                )

                Column {
                    if (uiState.isEmptyFeed) {
                        EmptyFeed()
                    }

                    if (uiState.isVisibleRefreshButton()) {
                        RefreshButton()
                    }
                    if (uiState.isProgess) {
                        Loading()
                    }
                }
            }
        }
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            if (uiState.snackBar != null)
                Snackbar {
                    Text(text = uiState.snackBar)
                }
        }
    }
}

@Composable
fun FeedsScreen(
    feedsViewModel: FeedsViewModel,
    onRefresh: (() -> Unit)? = null,
    clickProfile: ((Int) -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    onMenuClickListener: ((Int) -> Unit)? = null,
    onNameClickListener: ((Int) -> Unit)? = null,
    onRestaurantClickListener: ((Int) -> Unit)? = null,
    clickAddReview: ((Int) -> Unit)? = null,
    onLikeClickListener: ((Int) -> Unit)? = null,
    onCommentClickListener: ((Int) -> Unit)? = null,
    onShareClickListener: ((Int) -> Unit)? = null,
    onClickFavoriteListener: ((Int) -> Unit)? = null
) {
    val ss by feedsViewModel.uiState.collectAsState()

    FeedsScreen(
        uiState = ss,
        onRefresh = onRefresh,
        onNameClickListener = onNameClickListener,
        onRestaurantClickListener = onRestaurantClickListener,
        onMenuClickListener = onMenuClickListener,
        clickRestaurant = clickRestaurant,
        clickImage = clickImage,
        clickProfile = clickProfile,
        clickAddReview = clickAddReview,
        onShareClickListener = onShareClickListener,
        onCommentClickListener = onCommentClickListener,
        onLikeClickListener = onLikeClickListener,
        onClickFavoriteListener = onClickFavoriteListener
    )
}


@Composable
fun TestFeedsScreen(
    feedsViewModel: FeedsViewModel,
    onRefresh: (() -> Unit)? = null,
    clickProfile: ((Int) -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    onMenuClickListener: ((Int) -> Unit)? = null,
    onNameClickListener: ((Int) -> Unit)? = null,
    onRestaurantClickListener: ((Int) -> Unit)? = null,
    clickAddReview: ((Int) -> Unit)? = null,
    onLikeClickListener: ((Int) -> Unit)? = null,
    onCommentClickListener: ((Int) -> Unit)? = null,
    onShareClickListener: ((Int) -> Unit)? = null,
    onClickFavoriteListener: ((Int) -> Unit)? = null
) {
    val ss by feedsViewModel.uiState.collectAsState()

    FeedsScreen(uiState = ss,
        onRefresh = {
            feedsViewModel.refresh()
        },
        onNameClickListener = {
            feedsViewModel.clickName()
        },
        onRestaurantClickListener = {
            feedsViewModel.clickRestaurant()
        },
        onMenuClickListener = {
            feedsViewModel.clickMenu()
        },
        clickRestaurant = {
            feedsViewModel.clickRestaurant()
        },
        clickImage = {
            feedsViewModel.clickImage()
        },
        clickProfile = {
            feedsViewModel.clickProfile()
        },
        clickAddReview = {
            feedsViewModel.clickAddReview()
        },
        onShareClickListener = {
            feedsViewModel.clickShare()
        },
        onCommentClickListener = {
            feedsViewModel.clickComment()
        },
        onLikeClickListener = {
            feedsViewModel.clickLike()
        },
        onClickFavoriteListener = {
            feedsViewModel.clickFavorite()
        }
    )
}


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