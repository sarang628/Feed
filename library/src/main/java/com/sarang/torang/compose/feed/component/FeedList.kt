package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview

import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.data.feed.FeedCallBack
import com.sarang.torang.data.feed.FeedScreenConfig
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.imageHeight
private const val tag = "__FeedList"
@Composable
fun FeedList(
    modifier          : Modifier                    = Modifier,
    uiState           : FeedUiState                 = FeedUiState(),
    feedScreenState   : FeedScreenState             = rememberFeedScreenState(),
    feedCallBack      : FeedCallBack                = FeedCallBack(tag = tag),
    listContent       : LazyListScope.() -> Unit    = {},
    feedScreenConfig  : FeedScreenConfig            = FeedScreenConfig()
) {
    HandleOnFocusIndex(feedScreenState.listState, feedCallBack.onFocusItemIndex)
    if(feedScreenConfig.onBackToTop) BackToTopOnBackPress(feedScreenState.listState)

    val playingIndex by remember {
        derivedStateOf {
            val layoutInfo = feedScreenState.listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo

            if (visibleItems.isEmpty()) return@derivedStateOf -1

            // 화면에 가장 많이 보이는 아이템
            visibleItems.maxByOrNull { item ->
                val visibleHeight =
                    minOf(
                        item.offset + item.size,
                        layoutInfo.viewportEndOffset
                    ) - maxOf(item.offset, layoutInfo.viewportStartOffset)

                visibleHeight
            }?.index ?: -1
        }
    }

    val isScrollStopped = !feedScreenState.listState.isScrollInProgress

    RefreshAndBottomDetectionLazyColumn(
        modifier                 = modifier,
        count                    = uiState.list.size,
        onBottom                 = feedCallBack.onBottom,
        userScrollEnabled        = feedScreenConfig.scrollEnabled,
        pullToRefreshLayoutState = feedScreenState.pullToRefreshLayoutState,
        listState                = feedScreenState.listState,
        onRefresh                = feedCallBack.onRefresh,
    ) {
        items(count = uiState.list.size,
              key = { uiState.list[it].reviewId }){
            LocalFeedCompose.current.invoke(
                FeedTypeData(
                    feed            = uiState.list[it],
                    onLike          = feedCallBack.onLike,
                    onFavorite      = feedCallBack.onFavorite,
                    onVideoClick    = { feedCallBack.onVideoClick.invoke(uiState.list[it].reviewId) },
                    pageScrollable  = feedScreenConfig.pageScrollable,
                    isLogin         = uiState.isLogin,
                    imageHeight     = uiState.imageHeight(
                        density = LocalDensity.current,
                        screenWidthDp = LocalConfiguration.current.screenWidthDp,
                        screenHeightDp = LocalConfiguration.current.screenHeightDp
                    ),
                    isPlaying = (playingIndex == it) && isScrollStopped
                )
            )
        }

        listContent.invoke(this)
    }
}