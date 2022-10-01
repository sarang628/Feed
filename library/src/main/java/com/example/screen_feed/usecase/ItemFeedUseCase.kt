package com.example.screen_feed.usecase

import androidx.recyclerview.widget.RecyclerView
import com.sryang.torang_repository.data.remote.RemoteFeed


data class ItemFeedUIState(
    val itemId: Long,
    val itemFeedTopUiState: ItemFeedTopUIState,
    val itemFeedBottomUiState: ItemFeedBottomUIState,
    val reviewImages: ArrayList<String>
)

data class ItemFeedUseCase constructor(
    val itemId: Long,
    val itemFeedTopUseCase: ItemFeedTopUseCase,
    val itemFeedBottomUseCase: ItemFeedBottomUsecase,
    val pageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    val visibleReviewImage: Boolean = false
)

val ItemFeedUIState.pictureVisible: Boolean get() = reviewImages.isEmpty()


fun RemoteFeed.toItemTimeLineUIState(): ItemFeedUIState {
    return ItemFeedUIState(
        itemId = reviewId.toLong(),
        itemFeedTopUiState = ItemFeedTopUIState(
            reviewId = reviewId,
            name = name,
            restaurantName = restaurantName,
            rating = rating,
            profilePictureUrl = profilePictureUrl
        ),
        itemFeedBottomUiState = ItemFeedBottomUIState(
            reviewId = reviewId,
            likeAmount = 0,
            commentAmount = 0,
            author = "",
            comment = "",
            isLike = true,
            isFavorite = true
        ),
        reviewImages = arrayListOf("https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/")
    )
}

