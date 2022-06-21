package com.example.screen_feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.torang_core.*
import com.example.torang_core.navigation.*
import com.example.torang_core.util.EventObserver
import com.example.torang_core.util.Logger
import com.example.torang_core.navigation.TorangShare
import com.sarang.base_feed.FeedVH
import com.sarang.base_feed.ReportProcessor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

    /** 레스토랑 상세화면 네비게이션 */
    @Inject
    lateinit var restaurantDetailNavigation: RestaurantDetailNavigation

    /** 프로필 화면 네비게이션 */
    @Inject
    lateinit var profileNavigation: ProfileNavigation

    /** 피드 상세화면 네비게이션 */
    @Inject
    lateinit var timelineDetailNavigation: TimeLineDetailNavigation

    /** 공유 팝업 네비게이션 */
    @Inject
    lateinit var shareBottomSheetNavigation: ShareBottomSheetNavigation

    @Inject
    lateinit var torangShare: TorangShare

    /** 사진 페이지 네비게이션 */
    @Inject
    lateinit var picturePageNavigation: PicturePageNavigation

    /** 리뷰 추가 화면 네비게에션 */
    @Inject
    lateinit var addReviewNavigation: AddReviewNavigation

    /** 로그인 네비게이션 */
    @Inject
    lateinit var loginNavigation: LoginNavigation

    @Inject
    lateinit var menuBottomSheetNavigation: MenuBottomSheetNavigation

    @Inject
    lateinit var myMenuBottomSheetNavigation: MyMenuBottomSheetNavigation

    @Inject
    lateinit var notLoggedInMenuBottomSheetNavigation: NotLoggedInMenuBottomSheetNavigation

    @Inject
    lateinit var reportNavigation: ReportNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@FeedsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            addReviewNavigation = this@FeedsFragment.addReviewNavigation
            loginNavigation = this@FeedsFragment.loginNavigation
        }

        binding.rvTimelne.adapter = FeedsRvAdt(
            lifecycleOwner = viewLifecycleOwner,
            clickMenu = { viewModel.showMenu(it) },
            clickProfile = { viewModel.openProfile(it) },
            clickRestaurant = { viewModel.openTorangDetail(it) },
            clickLike = { v, i -> viewModel.clickLike(v, i) },
            clickComment = { viewModel.openFeedDetail(it) },
            clickShare = { viewModel.shareFeed(it) },
            clickFavorite = { v, i -> viewModel.clickFavorite(v, i) },
            clickPicture = { viewModel.openPicture(it) },
            getReviewImage = { viewModel.getReviewImages(it) },
            getLike = { viewModel.isLike(it) },
            getFavorite = { viewModel.isFavorite(it) }
        )

        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        viewModel.openFeedDetail.observe(viewLifecycleOwner,
            EventObserver { timelineDetailNavigation.go(requireContext(), it) })

        viewModel.openTorangDetail.observe(viewLifecycleOwner,
            EventObserver { restaurantDetailNavigation.go(requireContext(), it) })

        viewModel.showDeleteFeed.observe(viewLifecycleOwner,
            EventObserver { deleteFeed(it) })

        viewModel.shareFeed.observe(viewLifecycleOwner,
            EventObserver {
                torangShare.shareLink(
                    requireContext(),
                    "http://sarang628.iptime.org:91:/$it"
                )
            })

        viewModel.openProfile.observe(viewLifecycleOwner,
            EventObserver { profileNavigation.go(requireContext(), it) })

        viewModel.openPicture.observe(viewLifecycleOwner,
            EventObserver { picturePageNavigation.go(requireContext(), it.review_id, 0) })

        viewModel.openLogin.observe(viewLifecycleOwner,
            EventObserver {
                Logger.i("open login")
                loginNavigation.goLogin(requireContext())
            })

        viewModel.postOtherApp.observe(viewLifecycleOwner,
            EventObserver { myMenuBottomSheetNavigation.dismiss() })

        viewModel.store.observe(viewLifecycleOwner,
            EventObserver { myMenuBottomSheetNavigation.dismiss() })

        viewModel.openAddReview.observe(viewLifecycleOwner,
            EventObserver { addReviewNavigation.go(requireContext(), 0) })

        viewModel.modify.observe(viewLifecycleOwner,
            EventObserver {
                addReviewNavigation.go(
                    requireContext(),
                    restaurantId = it.restaurantId,
                    reviewId = it.review_id
                )
                myMenuBottomSheetNavigation.dismiss()
            })

        viewModel.hideLikeCount.observe(viewLifecycleOwner,
            EventObserver { myMenuBottomSheetNavigation.dismiss() })

        viewModel.lockReply.observe(viewLifecycleOwner,
            EventObserver { myMenuBottomSheetNavigation.dismiss() })

        viewModel.reasonContainThisPost.observe(viewLifecycleOwner,
            EventObserver { menuBottomSheetNavigation.dismiss() })

        viewModel.unfollow.observe(viewLifecycleOwner,
            EventObserver { menuBottomSheetNavigation.dismiss() })

        viewModel.hide.observe(viewLifecycleOwner,
            EventObserver { menuBottomSheetNavigation.dismiss() })

        viewModel.delete.observe(viewLifecycleOwner, EventObserver {
            myMenuBottomSheetNavigation.dismiss()
            deleteFeed(it)
        })

        viewLifecycleOwner.lifecycle.addObserver(
            ReportProcessor(
                viewModel,
                menuBottomSheetNavigation,
                myMenuBottomSheetNavigation,
                notLoggedInMenuBottomSheetNavigation,
                reportNavigation,
                requireContext()
            )
        )

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setMessage(it)
                .setPositiveButton("확인") { _, _ -> }
                .show()
        }

    }

    private fun deleteFeed(reviewId: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage("피드를 삭제하시겠습니까?")
            .setPositiveButton(
                "예"
            ) { _, _ -> viewModel.deleteFeed(reviewId) }
            .setNegativeButton(
                "아니오"
            ) { _, _ -> }
            .show()
    }
}