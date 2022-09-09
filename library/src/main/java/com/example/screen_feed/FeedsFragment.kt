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
import com.example.screen_feed.adapters.FeedsAdapter
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.databinding.ItemTimeLineBinding
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.usecase.*
import com.example.screen_feed.viewmodels.FeedsViewModel
import com.google.android.material.snackbar.Snackbar
import com.sryang.torang_core.navigation.LoginNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject

/**
 * 피드화면에서 사용하는 레이아웃
 * 레이아웃                      [FragmentFeedsBinding]
 * 리스트 아이템 레이아웃            [ItemTimeLineBinding]
 * 레아아웃 유즈케이스              [FeedsFragmentLayoutUseCase] feeds_fragment.xml
 * 리스트아이템 레이아웃 유즈케이스    [ItemFeedUseCase] item_time_line.xml
 * [FeedsAdapter]
 * [FeedsViewModel]
 * [FeedsUIstate]
 */
@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private val TAG = "FeedsFragment"

    /** 뷰모델 */
    private val viewModel: FeedsViewModel by viewModels()

    /** 화면 이동 네비게이션 */
    @Inject
    lateinit var navigation: FeedsFragmentNavigation

    @Inject
    lateinit var loginNavigation: LoginNavigation

    private var layoutUseCaseFlow: MutableStateFlow<FeedsFragmentLayoutUseCase> = MutableStateFlow(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedsBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            layoutUseCaseFlow.apply {
                subScribeUseCase(this)
                viewModel.subScribeUiState(this)
            }
        }
        return binding.root
    }

    private fun FeedsViewModel.subScribeUiState(layoutUsecaseFlow: MutableStateFlow<FeedsFragmentLayoutUseCase>) {
        viewLifecycleOwner.lifecycleScope.launch {
            feedsUiState.collect { feedUiState ->
                layoutUsecaseFlow.update {
                    it.copy(
                        isEmptyFeed = feedUiState.isEmptyFeed,
                        isRefreshing = feedUiState.isRefresh
                    )
                }

                feedUiState.toastMsg?.let { snackBar(it) }

                feedUiState.feedItemUiState?.let { itemFeedUIStates ->
                    (layoutUsecaseFlow.value.adapter as FeedsAdapter?)
                        ?.setFeeds(itemFeedUIStates.toItemTimelineUseCase())
                }

                if (feedUiState.goLogin != null && feedUiState.goLogin) {
                    consumeGoLogin()
                    loginNavigation.goLogin(requireContext())
                }

            }
        }
    }

    private fun FragmentFeedsBinding.subScribeUseCase(layoutUseCase: MutableStateFlow<FeedsFragmentLayoutUseCase>) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                layoutUseCase.collect(FlowCollector {
                    this@subScribeUseCase.useCase = it
                })
            }
        }
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
                onLikeClickListener = {
                    Toast.makeText(context, "click like", Toast.LENGTH_SHORT).show()
                    //viewModel.clickLike(it)
                },
                onCommentClickListener = { navigation.moveComment(requireContext()) },
                onShareClickListener = { navigation.showShare(requireContext()) },
                onClickFavoriteListener = {
                    Toast.makeText(context, "click favorite", Toast.LENGTH_SHORT).show()
                    //viewModel.clickFavorite(it)
                }
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

    private fun ArrayList<ItemFeedUIState>.toItemTimelineUseCase(): ArrayList<ItemFeedUseCase> {
        val list = this.stream()
            .map { generateItemFeedUseCase(it) }
            .collect(Collectors.toList())
        return list as ArrayList<ItemFeedUseCase>
    }
}