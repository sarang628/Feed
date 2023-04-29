package com.example.screen_feed

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.adapters.FeedsRecyclerViewAdapter
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.*
import com.google.android.material.snackbar.Snackbar
import getTestFeedUiState
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
            getTestFeedUiState(viewLifecycleOwner, requireContext(), binding.root),
            binding
        )

        return binding.root
    }

    private fun subScribeUiState(
        uiState: StateFlow<FeedsUIstate>, binding: FragmentFeedsBinding
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.collect { feedUiState ->
                feedUiState.feedItemUiState?.let { adapter.setFeeds(it) }
                binding.slTimeline.isRefreshing = feedUiState.isRefresh
            }
        }
    }
}