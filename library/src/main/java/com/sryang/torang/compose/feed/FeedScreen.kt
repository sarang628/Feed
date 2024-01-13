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
import com.sryang.torang.viewmodels.FeedsViewModel

@Composable
fun FeedScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    onAddReview: (() -> Unit),
    onComment: ((Int) -> Unit)? = null,
    onShare: ((Int) -> Unit)? = null,
    onMenu: ((Int) -> Unit)? = null,
    onName: ((Int) -> Unit)? = null,
    onRestaurant: ((Int) -> Unit)? = null,
    onImage: ((Int) -> Unit)? = null,
    onProfile: ((Int) -> Unit)? = null,
    feeds: @Composable (
        list: List<Feed>,
        onRefresh: (() -> Unit),
        onBottom: (() -> Unit),
        isRefreshing: Boolean,
    ) -> Unit
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()

    feedsViewModel.initialize(
        onComment, onShare, onMenu, onName, onRestaurant, onImage, onProfile
    )

    FeedScreen(uiState = uiState,
        onAddReview = onAddReview,
        feeds = {
            feeds.invoke(
                list = uiState.list,
                isRefreshing = uiState.isRefreshing,
                onBottom = { feedsViewModel.onBottom() },
                onRefresh = { feedsViewModel.refreshFeed() },
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
    uiState: FeedUiState,           /* ui state */
    onAddReview: (() -> Unit),   /* click add review */
    feeds: @Composable () -> Unit,  /* feed list ui module(common) */
    consumeErrorMessage: () -> Unit /* consume error message */
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val interactionSource = remember { MutableInteractionSource() }

    // snackbar process
    LaunchedEffect(key1 = uiState.error, block = {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            consumeErrorMessage.invoke()
        }
    })

    // snackbar + topAppBar + feedList
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                                onAddReview.invoke()
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
        onAddReview = { /*TODO*/ }, consumeErrorMessage = {},
        feeds = {}
    )
}