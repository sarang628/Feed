package com.example.screen_feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.navigation.AddReviewNavigation
import com.example.navigation.FeedNavigations
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.RefreshFeed
import com.example.screen_feed.ui.FeedList
import com.example.screen_feed.ui.TorangToolbar
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import com.example.screen_feed.uistate.isVisibleRefreshButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private val TAG = "FeedsFragment"

    @Inject
    lateinit var addReviewNavigation: AddReviewNavigation

    @Inject
    lateinit var feedNavigation: FeedNavigations


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(context = requireContext()).apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycleScope.launch {
                    val uiState =
                        getTestSenarioFeedFragmentUIstate(viewLifecycleOwner, requireContext())
                    uiState.collect {
                        setContent {
                            FeedsScreen(uiState = it)
                        }
                    }
                }
            }
        }
    }
}

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