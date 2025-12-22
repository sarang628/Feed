package com.sarang.torang.compose.feed.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.data.feed.FeedCallBack
import com.sarang.torang.data.feed.FeedScreenConfig
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState

private const val tag : String = "__FeedScreen"
/**
 * NOTE: Both backToTop and onTop do not work when LocalBottomDetectingLazyColumnType is not set.
 * @sample FeedScreenEmptyPreview
 * @sample FeedScreenLoadingPreview
 * @sample FeedScreenSuccessPreview
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FeedScreen(
    modifier            : Modifier               = Modifier,
    feedScreenState     : FeedScreenState        = rememberFeedScreenState(),
    loadingUiState      : FeedLoadingUiState     = FeedLoadingUiState.Loading,
    feedUiState         : FeedUiState            = FeedUiState(),
    feedCallBack        : FeedCallBack           = FeedCallBack(tag = tag),
    feedScreenConfig    : FeedScreenConfig       = FeedScreenConfig()
) {
    AnimatedContent(
        modifier        = modifier,
        targetState     = loadingUiState,
        transitionSpec  = { fadeIn(tween(800)) togetherWith
                            fadeOut(tween(800)) using
                            SizeTransform(clip = false) }
    ) { uiState ->
        when (uiState) {
            FeedLoadingUiState.Loading  -> FeedShimmer()

            is FeedLoadingUiState.Empty -> RefreshAndBottomDetectionLazyColumn(
                pullToRefreshLayoutState    = feedScreenState.pullToRefreshLayoutState,
                onRefresh                   = feedCallBack.onRefresh,
                listContent                 = { item { EmptyFeed() } }
            )

            FeedLoadingUiState.Reconnect -> RefreshAndBottomDetectionLazyColumn(
                pullToRefreshLayoutState    = feedScreenState.pullToRefreshLayoutState,
                onRefresh                   = feedCallBack.onRefresh,
                listContent                 = { item{ ConnectItem(onConnect = feedCallBack.onConnect) } }
            )

            FeedLoadingUiState.Success -> FeedList(
                uiState          = feedUiState,
                feedScreenState  = feedScreenState,
                feedCallBack     = feedCallBack,
                feedScreenConfig = feedScreenConfig,
                listContent      = {
                    if(feedScreenConfig.showBottomProgress){
                        items(1){
                            Box(Modifier.fillMaxWidth()){
                                CircularProgressIndicator(modifier = Modifier.size(30.dp).align(Alignment.Center), strokeWidth = 2.dp)
                            }
                        }
                    }
                }
            )

            else -> {}
        }
    }
}