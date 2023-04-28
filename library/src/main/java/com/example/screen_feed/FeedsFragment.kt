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
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.*
import com.example.screen_feed.viewmodels.FeedsViewModel
import com.google.android.material.snackbar.Snackbar
import getTestFeedUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * 피드화면에서 사용하는 레이아웃
 * 레이아웃                      [FragmentFeedsBinding]
 * 리스트 아이템 레이아웃            [ItemTimeLineBinding]
 * 레아아웃 유즈케이스              [FeedsFragmentLayoutUseCase] feeds_fragment.xml
 * 리스트아이템 레이아웃 유즈케이스    [ItemFeedUseCase] item_time_line.xml
 * [FeedsRecyclerViewAdapter]
 * [FeedsViewModel]
 * [FeedsUIstate]
 */
//@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private val TAG = "FeedsFragment"

    private val viewModel: FeedsViewModel by viewModels()

    //    @Inject
    lateinit var navigation: FeedsFragmentNavigation

    val adapter = FeedsRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvTimelne.adapter = adapter
        binding.slTimeline.setOnRefreshListener { viewModel.reload() }
        binding.button.setOnClickListener {
            // 로그인 상태 시 리뷰작성 화면 이동
            if (viewModel.feedsUiState.value.isLogin) {
                navigation.goWriteReview(requireContext())
            } else {
                // 비 로그인 상태 시 로그인 화며으로 이동
                navigation.goLogin(requireContext())
            }
        }

//        subScribeUiState(viewModel.feedsUiState)
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
//                feedUiState.toastMsg?.let { snackBar(it) }
                feedUiState.feedItemUiState?.let { adapter.setFeeds(it) }
                binding.slTimeline.isRefreshing = feedUiState.isRefresh
            }
        }
    }

    private fun snackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun deleteFeed(reviewId: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage("피드를 삭제하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("예") { _, _ -> viewModel.deleteFeed(reviewId) }
            .setNegativeButton("아니오") { _, _ -> }
            .show()
    }
}