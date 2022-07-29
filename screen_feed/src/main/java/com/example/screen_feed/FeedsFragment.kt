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
import com.example.screen_feed.adapters.FeedsAdapter
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.FeedsFragmentLayoutUseCase
import com.example.screen_feed.usecase.ItemFeedUIState
import com.example.screen_feed.usecase.ItemFeedTopUseCase
import com.example.screen_feed.usecase.ItemFeedBottomUsecase
import com.example.screen_feed.usecase.ItemFeedUseCase
import com.example.screen_feed.viewmodels.TestFeedsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject

/**
 * 피드화면에서 사용하는 레이아웃
 * [FeedsFragmentLayoutUseCase] feeds_fragment.xml
 * [ItemFeedUseCase] item_time_line.xml
 *
 *
 * [FeedsAdapter]
 * [ItemTimeLineBinding]
 * [FragmentFeedsBinding]
 * [FeedsViewModel]
 * [FeedsUIstate]
 */
@AndroidEntryPoint
class FeedsFragment : Fragment() {

    /** 뷰모델 */
    private val viewModel: TestFeedsViewModel by viewModels()

    /** 화면 이동 네비게이션 */
    @Inject
    lateinit var navigation: FeedsFragmentNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFeedsBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            subScribeUI(this, initFeedsFragmentLayoutUseCase())
        }.root
    }

    private fun initFeedsFragmentLayoutUseCase(): MutableStateFlow<FeedsFragmentLayoutUseCase> {
        val layoutUseCase = MutableStateFlow(
            FeedsFragmentLayoutUseCase(
                adapter = FeedsAdapter(), // 리사이클러뷰 아답터 설정
                onRefreshListener = { viewModel.reload() }, // 스와이프 하여 리프레시
                onMenuItemClickListener = { // 리뷰 추가 클릭
                    viewModel.clickAddReview()
                    false
                },
                reLoad = { viewModel.reload() }, // 갱신 버튼 클릭
                isEmptyFeed = true,
                isRefreshing = false
            )
        )
        return layoutUseCase
    }

    private fun subScribeUI(
        binding: FragmentFeedsBinding, layoutUseCase: MutableStateFlow<FeedsFragmentLayoutUseCase>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.feedsUiState.collect { feedUiState ->
                    layoutUseCase.update {
                        it.copy(
                            isEmptyFeed = feedUiState.isEmptyFeed,
                            isRefreshing = feedUiState.isRefresh
                        )
                    }

                    feedUiState.toastMsg?.let { snackBar(it) }

                    feedUiState.feedItemUiState?.let {
                        (binding.useCase?.adapter as FeedsAdapter).setFeeds(toItemTimelineUseCase(it))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                layoutUseCase.collect(FlowCollector {
                    binding.useCase = it
                })
            }
        }
    }

    private fun toItemTimelineUseCase(it: java.util.ArrayList<ItemFeedUIState>): ArrayList<ItemFeedUseCase> {
        val list = it.stream()
            .map { generateItemFeedUseCase(it) }
            .collect(Collectors.toList())
        return list as ArrayList<ItemFeedUseCase>
    }

    private fun generateItemFeedUseCase(it: ItemFeedUIState): ItemFeedUseCase {
        return ItemFeedUseCase(
            itemId = it.itemId,
            itemFeedTopUseCase = ItemFeedTopUseCase(
                data = it.itemFeedTopUiState,
                onMenuClickListener = { navigation.showMenu(requireContext()) },
                onProfileImageClickListener = { navigation.showProfile(requireContext()) },
                onNameClickListener = { navigation.showProfile(requireContext()) },
                onRestaurantClickListener = { navigation.moveRestaurant(requireContext()) }
            ),
            itemFeedBottomUseCase = ItemFeedBottomUsecase(
                data = it.itemFeedBottomUiState,
                onLikeClickListener = { viewModel.clickLike(it) },
                onCommentClickListener = { navigation.moveComment(requireContext()) },
                onShareClickListener = { navigation.showShare(requireContext()) },
                onClickFavoriteListener = { viewModel.clickFavorite(it) }
            ),
            pageAdapter = FeedPagerAdapter().apply {
                setList(it.reviewImages)
            }
        )
    }

    private fun snackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}