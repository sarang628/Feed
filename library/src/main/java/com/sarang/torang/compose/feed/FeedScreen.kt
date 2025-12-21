package com.sarang.torang.compose.feed

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.component.BackToTopOnBackPress
import com.sarang.torang.compose.feed.component.Connect
import com.sarang.torang.compose.feed.component.EmptyFeed
import com.sarang.torang.compose.feed.component.FeedListScreen
import com.sarang.torang.compose.feed.component.FeedShimmer
import com.sarang.torang.compose.feed.component.FeedTopAppBar
import com.sarang.torang.compose.feed.component.HandleOnFocusIndex
import com.sarang.torang.compose.feed.component.RefreshAndBottomDetectionLazyColumn
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.data.feed.FeedCallBack
import com.sarang.torang.data.feed.FeedScreenConfig
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import kotlinx.coroutines.delay


/**
 * @sample FeedScreenEmptyPreview
 * @sample FeedScreenLoadingPreview
 * @sample FeedScreenSuccessPreview
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FeedScreen(
    modifier            : Modifier               = Modifier,
    feedScreenState     : FeedScreenState        = rememberFeedScreenState(),
    tag                 : String                 = "__FeedScreen",
    loadingUiState      : FeedLoadingUiState     = FeedLoadingUiState.Loading,
    feedUiState         : FeedUiState            = FeedUiState(),
    topAppBar           : @Composable () -> Unit = {},
    feedCallBack        : FeedCallBack           = FeedCallBack(tag = tag),
    contentWindowInsets : WindowInsets           = ScaffoldDefaults.contentWindowInsets,
    feedScreenConfig    : FeedScreenConfig       = FeedScreenConfig()
) {
    HandleOnFocusIndex(feedScreenState.listState, feedCallBack.onFocusItemIndex)
    if(feedScreenConfig.onBackToTop) BackToTopOnBackPress(feedScreenState.listState)

    Scaffold(modifier            = modifier,
             snackbarHost        = { SnackbarHost(hostState = feedScreenState.snackbarState) },
             topBar              = topAppBar,
             contentWindowInsets = contentWindowInsets
    ) { padding ->
        Box {
            FeedListScreen(
                modifier        = Modifier.padding(padding),
                uiState         = feedUiState,
                feedScreenState = feedScreenState,
                scrollEnabled   = feedScreenConfig.scrollEnabled,
                feedCallBack    = feedCallBack,
                pageScrollable  = feedScreenConfig.pageScrollable,
                listContent     = {
                    if(feedScreenConfig.showBottomProgress){
                        items(1){
                            Box(Modifier.fillMaxWidth()){
                                CircularProgressIndicator(modifier = Modifier.size(30.dp).align(Alignment.Center), strokeWidth = 2.dp)
                            }
                        }
                    }
                }
            )

            AnimatedContent(
                targetState     = loadingUiState,
                transitionSpec  = { fadeIn(tween(800)) with
                                    fadeOut(tween(800)) using
                                    SizeTransform(clip = false) }
            ) { uiState ->
                when (uiState) {
                    FeedLoadingUiState.Loading -> FeedShimmer(
                        modifier = Modifier.testTag("shimmer")
                                           .fillMaxSize()
                                           .padding(padding)
                    )

                    is FeedLoadingUiState.Empty -> RefreshAndBottomDetectionLazyColumn(
                        modifier                    = Modifier.padding(padding),
                        listState                   = feedScreenState.listState,
                        pullToRefreshLayoutState    = feedScreenState.pullToRefreshLayoutState,
                        onRefresh                   = feedCallBack.onRefresh,
                        content                     = { EmptyFeed() },
                        count                       = 1,
                        itemCompose                 = {Box(Modifier.height(1000.dp)
                                                                   .fillMaxWidth()){} }
                    )

                    is FeedLoadingUiState.Success -> {}

                    FeedLoadingUiState.Reconnect -> RefreshAndBottomDetectionLazyColumn(
                        listState                   = feedScreenState.listState,
                        pullToRefreshLayoutState    = feedScreenState.pullToRefreshLayoutState,
                        content                     = { Connect(padding = padding,
                                                                onConnect = feedCallBack.onConnect)
                                                      }
                        )

                    is FeedLoadingUiState.Error -> {}
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Loading)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenEmptyPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Empty)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenSuccessPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Success,
        topAppBar = { FeedTopAppBar() }
    )
}

@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun TransitionPreview(){
    var uiState : FeedLoadingUiState by remember { mutableStateOf(FeedLoadingUiState.Loading) }

    LaunchedEffect(uiState) {
        delay(1000)
        uiState = FeedLoadingUiState.Success
    }

    FeedScreen(/*Preview*/
        loadingUiState = uiState)
}

@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun PreviewReconnect(){
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Reconnect)
}