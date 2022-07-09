package com.example.screen_feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.screen_feed.databinding.FragmentFeedsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * [FeedsRvAdt]
 * [FeedVH]
 * [ItemTimeLineBinding]
 * [FragmentTimeLineBinding]
 * [FeedsViewModel]
 */
@AndroidEntryPoint
class FeedsFragment : Fragment() {

    /** 데이터 바인딩 */
    private lateinit var binding: FragmentFeedsBinding

    /** 뷰모델 */
    private val viewModel: FeedsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@FeedsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.rvTimelne.adapter = FeedsRvAdt(lifecycleOwner = viewLifecycleOwner)

        binding.slTimeline.setOnRefreshListener {
            viewModel.reload()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.feedsUiState.collect {
                    if (!it.isRefresh) {
                        binding.slTimeline.isRefreshing = it.isRefresh
                    }
                    binding.button.visibility = if (it.isEmptyFeed) View.VISIBLE else View.GONE
                }
            }
        }
        return binding.root
    }
}