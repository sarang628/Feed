package com.sryang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.data.CommentDataUiState
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.usecase.FeedService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val feedService: FeedService
) : ViewModel()
{

    // UIState
    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    init
    {
        viewModelScope.launch {
            feedService.feeds.collect { newData -> _uiState.update { it.copy(list = newData) } } // feed 리스트 수집
            getFeed() // feed 가져오기
        }
    }

    // 피드 리스트 갱신
    fun refreshFeed()
    {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            getFeed() // feed 가져오기
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    // 피드 가져오기
    private suspend fun getFeed()
    {
        try
        {
            feedService.getFeeds()
        } catch (e: UnknownHostException)
        {
            Log.e("FeedsViewModel", e.toString())
            _uiState.update { it.copy(isFailedLoadFeed = true) }
        } catch (e: Exception)
        {
            Log.e("FeedsViewModel", e.toString())
        }
    }

    // 커멘트 가져오기
    fun onComment(reviewId: Int)
    {
        viewModelScope.launch {
            try
            {
                val result: CommentDataUiState = feedService.getComment(reviewId)
                _uiState.update {
                    it.copy(
                        selectedReviewId = reviewId,
                        showCommentDialog = true,
                        comments = result.commentList,
                        myProfileUrl = result.myProfileUrl
                    )
                }
            } catch (e: Exception)
            {
                Log.e("FeedsViewModel", e.toString())
            }
        }
    }

    // 공유 클릭
    fun onShare()
    {
        viewModelScope.launch {
            _uiState.update { it.copy(showShareDialog = true) }
        }
    }

    // 즐겨찾기 클릭
    fun onFavorite(reviewId: Int)
    {
        viewModelScope.launch {
            val review = uiState.value.list.find { it.reviewId == reviewId }
            review?.let {
                try
                {
                    if (it.isFavorite)
                    {
                        feedService.deleteFavorite(reviewId)
                    } else
                    {
                        feedService.addFavorite(reviewId)
                    }
                } catch (e: Exception)
                {
                    _uiState.update { it.copy(error = e.message) }
                }
            }
        }
    }

    // 좋아여 클릭
    fun onLike(reviewId: Int)
    {
        viewModelScope.launch {
            val review = uiState.value.list.find { it.reviewId == reviewId }
            review?.let {
                try
                {
                    if (it.isLike)
                    {
                        feedService.deleteLike(reviewId)
                    } else
                    {
                        feedService.addLike(reviewId)
                    }
                } catch (e: Exception)
                {
                    _uiState.update { it.copy(error = e.message) }
                }
            }
        }
    }

    // 메뉴 닫기
    fun closeMenu()
    {
        viewModelScope.launch {
            _uiState.update { (it.copy(showFeedMenuDialog = false)) }
        }
    }

    // 코멘트창 닫기
    fun closeComment()
    {
        viewModelScope.launch {
            _uiState.update { it.copy(showCommentDialog = false, selectedReviewId = null) }
        }
    }

    // 공유창 닫기
    fun closeShare()
    {
        viewModelScope.launch {
            _uiState.update { it.copy(showShareDialog = false) }
        }
    }

    // 메뉴 열기
    fun onMenu(reviewId: Int)
    {
        viewModelScope.launch {
            _uiState.update { it.copy(showFeedMenuDialog = true, selectedReviewId = reviewId) }
        }
    }

    // 코멘트 작성하기
    fun sendComment(comment: String)
    {
        viewModelScope.launch {
            uiState.value.selectedReviewId?.let { reviewId ->
                feedService.addComment(reviewId = reviewId, comment = comment)
                onComment(reviewId = reviewId)
            }
        }
    }

    // 에러메시지 삭제
    fun removeErrorMsg()
    {
        _uiState.update { it.copy(error = null) }
    }

    fun onReport()
    {
        _uiState.update { it.copy(showReportDialog = true, showFeedMenuDialog = false) }
    }

    fun closeReportDialog()
    {
        _uiState.update { it.copy(showReportDialog = false) }
    }

}