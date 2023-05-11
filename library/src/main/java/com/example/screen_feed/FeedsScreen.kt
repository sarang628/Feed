package com.example.screen_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
) {
    Column(
        Modifier.background(colorResource(id = R.color.colorSecondaryLight))
    ) {
        TorangToolbar(clickAddReview = {
            //feedNavigation.goAddReview(requireContext(), null)
        })
        Box {
            val pullRefreshState = rememberPullRefreshState(uiState.isRefreshing, onRefresh ?: { })
            val mod1 = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .verticalScroll(rememberScrollState())

            val mod2 = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)

            Box(
                modifier = if (uiState.feeds == null) mod1 else mod2
            ) {

                if (uiState.feeds != null)
                    FeedList(
                        clickProfile = {
                            //feedNavigation.goProfile(requireContext(), null)
                        },
                        list = uiState.feeds,
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = onRefresh
                    )

                PullRefreshIndicator(
                    refreshing = uiState.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

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
}

@Composable
fun FeedsScreen(feedsViewModel: FeedsViewModel) {
    val ss by feedsViewModel.uiState.collectAsState()
    FeedsScreen(uiState = ss, onRefresh = {
        feedsViewModel.refresh()
    })
}
