package com.sryang.torang.di.feed_di

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.base.feed.compose.feed.Feeds
import com.sryang.torang.BuildConfig
import com.sryang.torang.compose.FeedsScreen
import com.sryang.torang.viewmodels.FeedsViewModel

@Composable
fun FeedScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    clickAddReview: (() -> Unit),
    profileImageServerUrl: String = BuildConfig.PROFILE_IMAGE_SERVER_URL,
    onProfile: ((Int) -> Unit),
    onImage: ((Int) -> Unit),
    onName: (() -> Unit),
    onRestaurant: ((Int) -> Unit),
    imageServerUrl: String = BuildConfig.REVIEW_IMAGE_SERVER_URL,
    ratingBar: @Composable (Float) -> Unit
) {
    val uiState by feedsViewModel.uiState.collectAsState()

    Box {
        FeedsScreen(
            feeds = {
                Feeds(list = ArrayList(uiState.list.stream().map { it.review() }.toList()),
                    onProfile = onProfile,
                    onMenu = { },
                    onImage = onImage,
                    onName = { onName },
                    onLike = { feedsViewModel.onLike(it) },
                    onComment = { },
                    onShare = { },
                    onFavorite = { feedsViewModel.onFavorite(it) },
                    onRestaurant = onRestaurant,
                    profileImageServerUrl = profileImageServerUrl,
                    imageServerUrl = imageServerUrl,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { feedsViewModel.refreshFeed() },
                    ratingBar = {},
                    isEmpty = false,
                    isVisibleList = true,
                    isLoaded = true,
                    onBottom = {})
            },
            onAddReview = { clickAddReview.invoke() },
            errorComponent = {})
    }
}
