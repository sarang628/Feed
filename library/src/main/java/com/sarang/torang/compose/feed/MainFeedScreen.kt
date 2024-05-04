package com.sarang.torang.compose.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.MainFeedsViewModel

/**
 *
 */
@Composable
fun MainFeedScreen(
    feedsViewModel: MainFeedsViewModel = hiltViewModel(),
    feed: @Composable ((Feed) -> Unit)? = null,
    onAddReview: (() -> Unit)
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    val isRefreshing: Boolean by feedsViewModel.isRefreshing.collectAsState()

    feedsViewModel.initialize()

    _MainFeedScreen(
        uiState = uiState,
        onAddReview = onAddReview,
        isRefreshing = isRefreshing,
        onBottom = { feedsViewModel.onBottom() },
        onRefresh = { feedsViewModel.refreshFeed() },
        consumeErrorMessage = {
            feedsViewModel.clearErrorMsg()
        },
        feed = feed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun _MainFeedScreen(
    uiState: FeedUiState, /* ui state */
    onAddReview: (() -> Unit), /* click add review */
    consumeErrorMessage: () -> Unit, /* consume error message */
    onBottom: () -> Unit,
    feed: @Composable ((Feed) -> Unit)? = null,
    onRefresh: (() -> Unit),
    isRefreshing: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    FeedScreen(
        uiState = uiState,
        consumeErrorMessage = consumeErrorMessage,
        onBottom = onBottom,
        isRefreshing = isRefreshing,
        feed = feed,
        onRefresh = onRefresh,
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    Icon(imageVector = Icons.Outlined.AddCircle,
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
        }
    )
}

@Preview
@Composable
fun PreviewMainFeedScreen() {
    _MainFeedScreen(/*Preview*/
        /*Preview*/
        uiState = FeedUiState.Loading,
        onAddReview = { /*TODO*/ },
        consumeErrorMessage = {},
        onRefresh = {},
        onBottom = {},
        isRefreshing = false
    )
}