package com.example.screen_feed

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.FeedList
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.RefreshFeed
import com.example.screen_feed.ui.TorangToolbar
import com.example.screen_feed.uistate.FeedUiState
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.example.screen_feed.uistate.isVisibleRefreshButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// UIState 처리
@Composable
fun FeedsScreen(uiState: FeedsScreenUiState) {
    Column {
        TorangToolbar(clickAddReview = {
            //feedNavigation.goAddReview(requireContext(), null)
        })
        if (uiState.feeds != null)
            FeedList(
                clickProfile = {
                    //feedNavigation.goProfile(requireContext(), null)
                }, list = uiState.feeds
            )

        if (uiState.isEmptyFeed)
            EmptyFeed()

        if (uiState.isVisibleRefreshButton()) {
            RefreshFeed()
        }
        if (uiState.isProgess) {
            Loading()
        }
    }
}

@Composable
fun tt(feedsViewModel: FeedsViewModel) {
    val uiState by feedsViewModel.dd.collectAsState()
}

class FeedsViewModel : ViewModel() {
    val dd: StateFlow<String> = MutableStateFlow("")

}

@Composable
fun test1(s : StateFlow<FeedUiState>){
    val ss by s.collectAsState()
}