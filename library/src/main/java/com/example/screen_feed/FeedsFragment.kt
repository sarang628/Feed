package com.example.screen_feed

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.screen_feed.adapters.FeedsRecyclerViewAdapter
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.*
import com.example.screen_feed.viewmodels.FeedsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvTimelne.adapter = FeedsRecyclerViewAdapter()
        binding.slTimeline.setOnRefreshListener { viewModel.reload() }
        binding.button.setOnClickListener {
            if (viewModel.feedsUiState.value.isLogin) {                          // 로그인 상태 시 리뷰작성 화면 이동
                navigation.goWriteReview(requireContext())
            } else {                                                             // 비 로그인 상태 시 로그인 화며으로 이동
                navigation.goLogin(requireContext())
            }
        }

        subScribeUiState(viewModel.feedsUiState)

        return binding.root
    }

    private fun subScribeUiState(
        uiState: StateFlow<FeedsUIstate>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.collect { feedUiState ->
                feedUiState.toastMsg?.let { snackBar(it) }
                feedUiState.feedItemUiState?.let { itemFeedUIStates -> }
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