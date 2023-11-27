package com.posco.torang.di.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.base.feed.compose.feed.Feeds
import com.sryang.torang.BuildConfig
import com.sryang.torang.compose.FeedsScreen
import com.sryang.torang.viewmodels.FeedsViewModel

@Composable
fun FeedScreen(
    profileImageServerUrl: String = BuildConfig.PROFILE_IMAGE_SERVER_URL,
    imageServerUrl: String = BuildConfig.REVIEW_IMAGE_SERVER_URL,
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    clickAddReview: (() -> Unit),
    onProfile: ((Int) -> Unit),
    onImage: ((Int) -> Unit),
    onName: (() -> Unit),
    onRestaurant: ((Int) -> Unit),
    onMenu: ((Int) -> Unit),
    onComment: ((Int) -> Unit),
    onShare: ((Int) -> Unit),
    ratingBar: @Composable (Float) -> Unit
) {
    val uiState by feedsViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.error, block = {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            feedsViewModel.clearErrorMsg()
        }
    })

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            FeedsScreen(
                feeds = {
                    Feeds(list = ArrayList(uiState.list.map { it.review() }),
                        onProfile = onProfile,
                        onMenu = onMenu,
                        onImage = onImage,
                        onName = { onName },
                        onLike = { feedsViewModel.onLike(it) },
                        onComment = onComment,
                        onShare = onShare,
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
                errorComponent = {},
            )
        }
    }
}
