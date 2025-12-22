package com.sarang.torang.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.component.FeedScreen
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.RefreshIndicatorState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.FeedCallBack
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefresh
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sryang.torang.ui.TorangTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FeedScreenTest(feedScreenState: FeedScreenState = rememberFeedScreenState()) {
    val scope = rememberCoroutineScope()
    var uiState: FeedLoadingUiState by remember { mutableStateOf(FeedLoadingUiState.Loading) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    CompositionLocalProvider(
        LocalFeedCompose provides CustomFeedCompose,
        LocalPullToRefreshLayoutType provides customPullToRefresh,
        LocalExpandableTextType provides CustomExpandableTextType,
        LocalFeedImageLoader provides { CustomFeedImageLoader().invoke(it) },
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
    ) {
        val feedScreen : @Composable ()->Unit = {
            FeedScreen(
                feedUiState     = FeedUiState(list = listOf(Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample),
                                              isLogin = false),
                loadingUiState  = uiState,
                feedScreenState = feedScreenState,
                feedCallBack    = FeedCallBack(
                    onConnect = { feedScreenState.refresh(true) }
                )
            )
        }
        feedScreen.invoke()

        val bottomSheetContent : @Composable ColumnScope.() -> Unit = {
            OperationButtons(
                onTop       = { scope.launch { feedScreenState.onTop() }
                                scope.launch { feedScreenState.showSnackBar("click on top") } },
                onLoading   = { uiState = FeedLoadingUiState.Loading },
                onSuccess   = { uiState = FeedLoadingUiState.Success },
                onError     = { uiState = FeedLoadingUiState.Error("error") },
                onEmpty     = { uiState = FeedLoadingUiState.Empty },
                onReconnect = { uiState = FeedLoadingUiState.Reconnect },
                onRefresh   = {
                    when (feedScreenState.pullToRefreshLayoutState.refreshIndicatorState.value) {
                        RefreshIndicatorState.Default -> feedScreenState.refresh(true)
                        else -> feedScreenState.refresh(false)
                    }
                }
            )
        }
        
        val floatActionButton : @Composable () -> Unit = {
            FloatingActionButton(
                onClick = { scope.launch { scaffoldState.bottomSheetState.expand() } },
                content = { Icon(Icons.Default.Menu, contentDescription = null) })
        }

        TorangTheme {
            BottomSheetScaffold(scaffoldState = scaffoldState,
                                sheetPeekHeight = 0.dp,
                                sheetContent = bottomSheetContent) {
                Scaffold(floatingActionButton = floatActionButton) {
                    Box(Modifier.padding(it)){
                        feedScreen.invoke()
                    }
                }
            }
        }
    }
}