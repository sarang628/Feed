package com.example.screen_feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.databinding.ItemTimeLineBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [FeedsRvAdt]
 * [ItemTimeLineBinding]
 * [FragmentFeedsBinding]
 * [FeedsViewModel]
 */
@AndroidEntryPoint
class FeedsFragment : Fragment() {

    /** 뷰모델 */
    private val viewModel: FeedsViewModel by viewModels()

    /** 화면 이동 네비게이션 */
    @Inject
    lateinit var navigation: FeedsFragmentNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 바인딩 초기화
        val binding: FragmentFeedsBinding =
            FragmentFeedsBinding.inflate(layoutInflater, container, false)
        // 리사이클러뷰 아답터 설정
        binding.rvTimelne.adapter = FeedsRvAdt(
            lifecycleOwner = viewLifecycleOwner,
            navigation = navigation
        )
        // 스와이프 하여 리프레시
        binding.slTimeline.setOnRefreshListener {
            viewModel.reload()
        }
        // 리뷰 추가 클릭
        binding.toolbar2.setOnMenuItemClickListener {
            viewModel.clickAddReview()
            false
        }
        // 갱신 버튼 클릭
        binding.button.setOnClickListener {
            viewModel.reload()
        }

        subScribeUI(binding)

        return binding.root
    }

    private fun subScribeUI(binding: FragmentFeedsBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.feedsUiState.collect {
                    binding.slTimeline.isRefreshing = it.isRefresh
                    binding.button.visibility = if (it.isEmptyFeed) View.VISIBLE else View.GONE

                    it.toastMsg?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}