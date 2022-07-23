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
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.usecase.FeedsFragmentLayoutUseCase
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [FeedsAdapter]
 * [ItemTimeLineBinding]
 * [FragmentFeedsBinding]
 * [FeedsViewModel]
 */
@AndroidEntryPoint
class FeedsFragment : Fragment() {

    /** 뷰모델 */
    private val viewModel: TestFeedsViewModel by viewModels()

    /** 화면 이동 네비게이션 */
    @Inject
    lateinit var navigation: FeedsFragmentNavigation
    lateinit var layoutUseCase : MutableStateFlow<FeedsFragmentLayoutUseCase>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 바인딩 초기화
        val binding = FragmentFeedsBinding.inflate(layoutInflater, container, false)
        initUIUseCase(binding)
        subScribeUI(binding)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    private fun initUIUseCase(binding: FragmentFeedsBinding) {

        layoutUseCase = MutableStateFlow(
            FeedsFragmentLayoutUseCase(
                adapter = FeedsAdapter(navigation = navigation), // 리사이클러뷰 아답터 설정
                onRefreshListener = { viewModel.reload() }, // 스와이프 하여 리프레시
                onMenuItemClickListener = { // 리뷰 추가 클릭
                    viewModel.clickAddReview()
                    false
                },
                reLoad = { viewModel.reload() }, // 갱신 버튼 클릭
                visibleButton = true,
                isRefreshing = false
            )
        )

        viewLifecycleOwner.lifecycleScope.launch {
            layoutUseCase.collect(FlowCollector {
                binding.useCase = it
            })
        }
    }

    private fun subScribeUI(binding: FragmentFeedsBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.feedsUiState.collect { feedUiState ->
                    layoutUseCase.update {
                        it.copy(
                            visibleButton = feedUiState.isEmptyFeed,
                            isRefreshing = feedUiState.isRefresh
                        )
                    }

                    feedUiState.toastMsg?.let {
                        Snackbar.make(binding.root, feedUiState.toastMsg, Snackbar.LENGTH_SHORT).show()
                    }

                    feedUiState.feedItemUiState?.let {
                        (binding.useCase?.adapter as FeedsAdapter).setFeeds(it)
                    }
                }
            }
        }
    }

}