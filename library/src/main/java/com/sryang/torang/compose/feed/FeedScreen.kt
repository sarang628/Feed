package com.sryang.torang.compose.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.torang.R
import com.sryang.torang.data.feed.Feed
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.uistate.isEmpty
import com.sryang.torang.viewmodels.FeedsViewModel

@Composable
fun FeedScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    clickAddReview: (() -> Unit),
    feeds: @Composable (
        list: List<Feed>,
        onLike: ((Int) -> Unit),
        onFavorite: ((Int) -> Unit),
        onRefresh: (() -> Unit),
        onBottom: (() -> Unit),
        isRefreshing: Boolean,
        isEmpty: Boolean
    ) -> Unit
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    FeedScreen(uiState = uiState,
        clickAddReview = clickAddReview,
        feeds = {
            feeds.invoke(
                list = uiState.list,
                isEmpty = uiState.isEmpty,
                isRefreshing = uiState.isRefreshing,
                onBottom = { feedsViewModel.onBottom() },
                onFavorite = { feedsViewModel.onFavorite(it) },
                onLike = { feedsViewModel.onLike(it) },
                onRefresh = { feedsViewModel.refreshFeed() }
            )
        },
        consumeErrorMessage = {
            feedsViewModel.clearErrorMsg()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    uiState: FeedUiState,
    clickAddReview: (() -> Unit),
    feeds: @Composable () -> Unit,
    consumeErrorMessage: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(key1 = uiState.error, block = {
        uiState.error?.let {
            snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            consumeErrorMessage.invoke()
        }
    })

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    Image(painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                clickAddReview.invoke()
                            })
                })
        }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        {
            feeds.invoke()
        }
    }
}

@Preview
@Composable
fun PreviewFeedScreen() {
    FeedScreen(
        uiState = FeedUiState(),
        clickAddReview = { /*TODO*/ }, consumeErrorMessage = {},
        feeds = {}
    )
}