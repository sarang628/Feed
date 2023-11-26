package com.posco.feedscreentestapp.di.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.posco.feedscreentestapp.BuildConfig
import com.sryang.base.feed.compose.feed.Feeds
import com.sryang.base.feed.compose.feed.TorangToolbar
import com.sryang.library.comment.CommentBottomSheetDialog
import com.sryang.library.feed_menu.FeedMenuBottomSheetDialog
import com.sryang.library.share.ShareBottomSheetDialog
import com.sryang.torang.compose.FeedsScreen
import com.sryang.torang.compose.report.ReportModal
import com.sryang.torang.compose.report.ReportScreen
import com.sryang.torang.viewmodels.FeedsViewModel

@Composable
fun FeedScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    clickAddReview: (() -> Unit),
    profileImageServerUrl: String = BuildConfig.PROFILE_IMAGE_SERVER_URL,
    onProfile: ((Int) -> Unit),
    onImage: ((Int) -> Unit),
    onName: (() -> Unit),
    onRestaurant: ((Int) -> Unit),
    imageServerUrl: String = BuildConfig.REVIEW_IMAGE_SERVER_URL,
    ratingBar: @Composable (Float) -> Unit
) {
    val uiState by feedsViewModel.uiState.collectAsState()

    Box {
        FeedsScreen(feedsViewModel = feedsViewModel,
            feeds = {
                Feeds(list = ArrayList(uiState.list.stream().map { it.review() }.toList()),
                    onProfile = onProfile,
                    onMenu = { feedsViewModel.onMenu(it) },
                    onImage = onImage,
                    onName = { onName },
                    onLike = { feedsViewModel.onLike(it) },
                    onComment = { feedsViewModel.onComment(it) },
                    onShare = { feedsViewModel.onShare() },
                    onFavorite = { feedsViewModel.onFavorite(it) },
                    onRestaurant = onRestaurant,
                    profileImageServerUrl = profileImageServerUrl,
                    imageServerUrl = imageServerUrl,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { feedsViewModel.refreshFeed() },
                    ratingBar = {},
                    isEmpty = false,
                    isVisibleList = true,
                    isLoaded = true,
                    onBottom = {})
            },
            onAddReview = { clickAddReview.invoke() },
            feedMenuBottomSheetDialog = {
                FeedMenuBottomSheetDialog(
                    isExpand = it,
                    onEdit = {},
                    onDelete = {},
                    onReport = { feedsViewModel.onReport() },
                    onClose = { feedsViewModel.closeMenu() },
                    isMine = false
                )
            },
            commentBottomSheetDialog = {
                CommentBottomSheetDialog(isExpand = it,
                    onSelect = {},
                    onClose = { feedsViewModel.closeComment() },
                    list = uiState.comments?.stream()?.map { it.toCommentItemUiState() }
                        ?.toList() ?: ArrayList(),
                    onSend = { feedsViewModel.sendComment(it) },
                    profileImageUrl = uiState.myProfileUrl ?: "",
                    profileImageServerUrl = profileImageServerUrl,
                    name = "name"
                )
            },
            shareBottomSheetDialog = {
                ShareBottomSheetDialog(
                    isExpand = true,
                    onSelect = {},
                    onClose = { feedsViewModel.closeShare() },
                    list = ArrayList()
                )
            },
            errorComponent = {},
            reportDialog = {
                uiState.selectedReviewId?.let {
                    ReportModal(
                        reviewId = it,
                        onReported = { feedsViewModel.closeReportDialog() },
                        profileServerUrl = profileImageServerUrl
                    )
                }
            })
    }
}
