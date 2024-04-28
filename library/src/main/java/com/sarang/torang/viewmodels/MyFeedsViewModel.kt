package com.sarang.torang.viewmodels

import com.sarang.torang.data.feed.Feed
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedRefreshUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyFeedsViewModel @Inject constructor(
    feedRefreshUseCase: FeedRefreshUseCase,
    addLikeUseCase: AddLikeUseCase,
    deleteLikeUseCase: DeleteLikeUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getFeedFlowUseCase: GetMyFeedFlowUseCase
) : FeedsViewModel(
    feedRefreshUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    object : GetFeedFlowUseCase{
        override suspend fun invoke(): Flow<List<Feed>> {
            return getFeedFlowUseCase.invoke()
        }
    }
)