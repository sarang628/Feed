package com.example.screen_feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.navigation.AddReviewNavigation
import com.example.screen_feed.adapters.FeedPagerAdapter
import com.example.screen_feed.adapters.FeedsRecyclerViewAdapter
import com.example.screen_feed.data.Feed
import com.example.screen_feed.databinding.FragmentFeedsBinding
import com.example.screen_feed.uistate.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList

@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private val TAG = "FeedsFragment"
    val adapter = FeedsRecyclerViewAdapter()

    @Inject
    lateinit var addReviewNavigation: AddReviewNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvTimelne.adapter = adapter

        subScribeUiState(
            getTestSenarioFeedFragmentUIstate(viewLifecycleOwner, requireContext(), binding.root),
            binding
        )

        binding.toolbar2.setOnMenuItemClickListener {
            addReviewNavigation.navigate(this)
            true
        }

        binding.slFeed.setOnRefreshListener {
            binding.slFeed.isRefreshing = false
        }

        return binding.root
    }

    // UIState 처리
    private fun subScribeUiState(
        uiState: StateFlow<FeedFragmentUIstate>, binding: FragmentFeedsBinding
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.collect { feedUiState ->
                binding.slFeed.isRefreshing = feedUiState.isRefreshing
                binding.btnRefresh.visibility = feedUiState.isVisibleRefreshButton()
                binding.pbFeed.visibility = if (feedUiState.isProgess) View.VISIBLE else View.GONE
                binding.tvEmpty.visibility =
                    if (feedUiState.isEmptyFeed) View.VISIBLE else View.GONE
                setFeed(feedUiState.feeds)
            }
        }
    }

    private fun setFeed(feeds: ArrayList<Feed>?) {
        if (feeds == null) {
            adapter.setFeeds(ArrayList())
            return
        }

        adapter.setFeeds(feeds.stream().map {
            FeedUiState(
                reviewId = it.reviewId
                ,itemFeedTopUiState = FeedTopUIState(
                    reviewId = it.reviewId,
                    name = it.name,
                    restaurantName = it.restaurantName,
                    rating = it.rating,
                    profilePictureUrl = it.profilePictureUrl,
                    onMenuClickListener = {},
                    onProfileImageClickListener = {},
                    onNameClickListener = {},
                    onRestaurantClickListener = {}
                )
            , itemFeedBottomUiState = FeedBottomUIState(
                    reviewId = it.reviewId,
                    likeAmount = it.likeAmount,
                    commentAmount = it.commentAmount,
                    author = it.author,
                    author1 = it.author1,
                    author2 = it.author2,
                    comment = it.comment,
                    comment1 = it.comment1,
                    comment2 = it.comment2,
                    isLike = it.isLike,
                    isFavorite = it.isFavorite,
                    contents = it.contents
            )
            , reviewImages = it.reviewImages
            , visibleReviewImage = true
            , pageAdapter = FeedPagerAdapter().apply {
                 it.reviewImages?.let { setList(it) }
                }
            , imageClickListener = {}
            )
        }.toList() as ArrayList<FeedUiState>)
    }
}