package com.example.screen_feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.adapters.FeedsRecyclerViewAdapter
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.uistate.FeedFragmentUIstate
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import com.example.screen_feed.uistate.isVisibleRefreshButton
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedsFragment : Fragment() {

    private val TAG = "FeedsFragment"
    val adapter = FeedsRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvTimelne.adapter = adapter

        subScribeUiState(
            getTestSenarioFeedFragmentUIstate(viewLifecycleOwner, requireContext(), binding.root),
            binding
        )

        return binding.root
    }

    private fun subScribeUiState(
        uiState: StateFlow<FeedFragmentUIstate>, binding: FragmentFeedsBinding
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.collect { feedUiState ->
                feedUiState.feedItemUiState?.let { adapter.setFeeds(it) }
                binding.slTimeline.isRefreshing = feedUiState.isRefresh
                binding.btnRefresh.visibility = feedUiState.isVisibleRefreshButton()
                binding.pbFeed.visibility = if (feedUiState.isProgess) View.VISIBLE else View.GONE
            }
        }
    }
}