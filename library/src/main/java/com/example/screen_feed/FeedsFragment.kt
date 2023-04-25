package com.example.screen_feed

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.screen_feed.adapters.FeedPagerAdapter
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
import java.util.stream.Collectors

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
        val layoutUsecaseFlow = MutableStateFlow(
            FeedsFragmentLayoutUseCase(
                adapter = FeedsRecyclerViewAdapter(
                    //lifecycleOwner = viewLifecycleOwner
                ), // 리사이클러뷰 아답터 설정
                onRefreshListener = { viewModel.reload() },                              // 스와이프 하여 리프레시
                onAddReviewClickListener = {                                             // 리뷰 추가 클릭
                    if (viewModel.feedsUiState.value.isLogin) {                          // 로그인 상태 시 리뷰작성 화면 이동
                        navigation.goWriteReview(requireContext())
                        false
                    } else {                                                             // 비 로그인 상태 시 로그인 화며으로 이동
                        navigation.goLogin(requireContext())
                        false
                    }
                },
                reLoad = { viewModel.reload() }                                          // 갱신 버튼 클릭
            )
        )

        //LayoutUsecase 구독
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                layoutUsecaseFlow.collect(FlowCollector {
                    binding.useCase = it
                })
            }
        }

        subScribeUiState(viewModel.feedsUiState, layoutUsecaseFlow)

        return binding.root
    }

    private fun subScribeUiState(
        uiState: StateFlow<FeedsUIstate>,
        layoutUsecaseFlow: MutableStateFlow<FeedsFragmentLayoutUseCase>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.collect { feedUiState ->
                layoutUsecaseFlow.update {
                    it.copy(
                        isEmptyFeed = feedUiState.isEmptyFeed,
                        isRefreshing = feedUiState.isRefresh,
                        isProgress = feedUiState.isProgess
                    )
                }

                feedUiState.toastMsg?.let { snackBar(it) }

                feedUiState.feedItemUiState?.let { itemFeedUIStates ->
                    (layoutUsecaseFlow.value.adapter as FeedsRecyclerViewAdapter?)
                        ?.setFeeds(itemFeedUIStates.toItemTimelineUseCase())
                }
            }
        }
    }

    //UIState 구독
    private fun generateItemFeedUseCase(it: ItemFeedUIState): ItemFeedUseCase {
        return ItemFeedUseCase(
            itemId = it.itemId,
            itemFeedTopUseCase = ItemFeedTopUseCase(
                data = it.itemFeedTopUiState,
                onMenuClickListener = {
                    deleteFeed(it)
                },
                onProfileImageClickListener = { navigation.showProfile(requireContext()) },
                onNameClickListener = { navigation.showProfile(requireContext()) },
                onRestaurantClickListener = { navigation.moveRestaurant(requireContext()) }
            ),
            itemFeedBottomUseCase = ItemFeedBottomUsecase(
                data = it.itemFeedBottomUiState,
                onLikeClickListener = {
                    viewModel.clickLike(it)
                },
                onCommentClickListener = { navigation.moveComment(requireContext()) },
                onShareClickListener = { navigation.showShare(requireContext()) },
                onClickFavoriteListener = {
                    viewModel.clickFavorite(it)
                },
                visibleLike = it.itemFeedBottomUiState.likeAmount > 0,
                visibleComment = it.itemFeedBottomUiState.commentAmount > 0
            ),
            pageAdapter = FeedPagerAdapter().apply {
                setList(it.reviewImages)
            },
            visibleReviewImage = !it.reviewImages.isEmpty()
        )
    }

    private fun snackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun ArrayList<ItemFeedUIState>.toItemTimelineUseCase(): ArrayList<ItemFeedUseCase> {
        val list = this.stream()
            .map { generateItemFeedUseCase(it) }
            .collect(Collectors.toList())
        return list as ArrayList<ItemFeedUseCase>
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