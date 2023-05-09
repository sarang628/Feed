package com.example.screen_feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.navigation.AddReviewNavigation
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.ui.EmptyFeed
import com.example.screen_feed.ui.Loading
import com.example.screen_feed.ui.RefreshFeed
import com.example.screen_feed.ui.SwipeRefreshTest
import com.example.screen_feed.ui.TorangToolbar
import com.example.screen_feed.uistate.FeedFragmentUIstate
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import com.example.screen_feed.uistate.isVisibleRefreshButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private val TAG = "FeedsFragment"

    @Inject
    lateinit var addReviewNavigation: AddReviewNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedsBinding.inflate(layoutInflater, container, false)

        subScribeUiState(
            getTestSenarioFeedFragmentUIstate(viewLifecycleOwner, requireContext(), binding.root),
            binding
        )
        return binding.root
    }

    // UIState 처리
    private fun subScribeUiState(
        uiState: StateFlow<FeedFragmentUIstate>, binding: FragmentFeedsBinding
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.collect { feedUiState ->
                binding.cvToolbar.setContent {
                    Column {
                        TorangToolbar(clickAddReview = {
                            addReviewNavigation.navigate(this@FeedsFragment)
                        })
                        if (feedUiState.feeds != null)
                            SwipeRefreshTest()

                        if (feedUiState.isEmptyFeed)
                            EmptyFeed()

                        if (feedUiState.isVisibleRefreshButton()) {
                            RefreshFeed()
                        }
                        if (feedUiState.isProgess) {
                            Loading()
                        }
                    }
                }
            }
        }
    }
}