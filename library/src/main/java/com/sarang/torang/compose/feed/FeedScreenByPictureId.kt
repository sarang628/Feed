package com.sarang.torang.compose.feed

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.data.feed.FeedCallBack
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.viewmodels.FeedScreenByPictureIdViewModel

// formatter : off
/**
 * ### 리뷰의 사용자 Feed 리스트
 * @param listState - feeds 항목에 같은 listState를 넣어줘야 프로필에서 피드 클릭 시 해당 항목으로 이동함.
 */
@Composable
fun FeedScreenByPictureId(
    pictureId       : Int,
    showLog         : Boolean                           = false,
    tag             : String                            = "__FeedScreenByPictureId",
    feedsViewModel  : FeedScreenByPictureIdViewModel    = hiltViewModel(),
    feedScreenState : FeedScreenState                   = rememberFeedScreenState(),
    onBack          : (() -> Unit)                      = { Log.i(tag, "onBack isn't set") },
) {
    val uiState         : FeedLoadingUiState    = feedsViewModel.uiState

    LaunchedEffect(key1 = pictureId) {
        feedsViewModel.getFeedByPictureId(pictureId)
    }

    LaunchedEffect(feedsViewModel.msgState) {
        if (feedsViewModel.msgState.isNotEmpty()) {
            feedScreenState.showSnackBar(feedsViewModel.msgState[0])
            feedsViewModel.removeTopErrorMessage()
        }
    }

    FeedByPictureId(
        uiState         = uiState,
        showLog         = showLog,
        feedUiState     = feedsViewModel.feedUiState,
        onBack          = onBack,
        onRefresh       = feedsViewModel::refreshFeed ,
        onBottom        = feedsViewModel::onBottom ,
        feedScreenState = feedScreenState
    )
}

// formatter : on
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedByPictureId(
    tag             : String                = "__FeedByPictureId",
    showLog         : Boolean               = false,
    uiState         : FeedLoadingUiState    = FeedLoadingUiState.Loading,
    feedUiState     : FeedUiState           = FeedUiState(),
    onBack          : () -> Unit            = { Log.i(tag, "onBack isn't set") },
    onRefresh       : (() -> Unit)          = { Log.i(tag, "onRefresh isn't set") },/*base feed 에서 제공*/
    onBottom        : (() -> Unit)          = { Log.i(tag, "onBottom isn't set") },/*base feed 에서 제공*/
    feedScreenState : FeedScreenState       = rememberFeedScreenState()
) {
    LaunchedEffect(feedUiState) {
        showLog.d(tag, feedUiState.toString())
    }
    FeedScreen(
        loadingUiState  = uiState,
        feedUiState     = feedUiState,
        feedScreenState = feedScreenState,
        feedCallBack    = FeedCallBack(
            onRefresh = onRefresh,
            onBottom = onBottom
        ),
        topAppBar       = {
            TopAppBar(
                title           = {
                                    Text(text = "Post",
                                         fontSize = 21.sp,
                                         fontWeight = FontWeight.Bold)
                                  },
                navigationIcon  = {
                                    IconButton(onClick = onBack) {
                                        Icon(imageVector        = Icons.AutoMirrored.Default.ArrowBack,
                                             contentDescription = "") }
                                   }
                    )
        },
    )
}

fun Boolean.d(tag: String, msg: String) {
    if(this)Log.d(tag, msg)
}
