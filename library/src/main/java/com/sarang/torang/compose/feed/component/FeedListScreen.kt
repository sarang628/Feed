package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity

import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.data.feed.FeedCallBack
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.imageHeight

@Composable
fun FeedListScreen(
    modifier        : Modifier          = Modifier,
    uiState         : FeedUiState       = FeedUiState(),
    tag             : String            = "__FeedSuccess",
    scrollEnabled   : Boolean           = true,
    feedScreenState : FeedScreenState   = rememberFeedScreenState(),
    feedCallBack    : FeedCallBack      = FeedCallBack(tag = tag),
    pageScrollable  : Boolean           = true,
    listContent     : LazyListScope.() -> Unit  = {},
) {
    RefreshAndBottomDetectionLazyColumn(
        modifier                 = modifier,
        count                    = uiState.list.size,
        onBottom                 = feedCallBack.onBottom,
        userScrollEnabled        = scrollEnabled,
        pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
        listState                = feedScreenState.listState,
        onRefresh                = feedCallBack.onRefresh,
        listContent              = listContent
    ) {
        LocalFeedCompose.current.invoke(
            FeedTypeData(
                feed            = uiState.list[it],
                onLike          = feedCallBack.onLike,
                onFavorite      = feedCallBack.onFavorite,
                onVideoClick    = { feedCallBack.onVideoClick.invoke(uiState.list[it].reviewId) },
                pageScrollable  = pageScrollable,
                isLogin         = uiState.isLogin,
                imageHeight     = uiState.imageHeight(
                    density = LocalDensity.current,
                    screenWidthDp = LocalConfiguration.current.screenWidthDp,
                    screenHeightDp = LocalConfiguration.current.screenHeightDp
                )
            )
        )
    }
}