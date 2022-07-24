package com.example.screen_feed.usecase

import androidx.recyclerview.widget.RecyclerView
import com.example.screen_feed.FeedPagerAdapter
import com.example.torang_core.data.remote.RemoteFeed

data class ItemTimeLineUseCase(
    val itemFeedTopUseCase: ItemFeedTopUseCase,
    val itemFeedBottomUseCase: ItemFeedBottomUsecase,
    val pageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>
)


fun RemoteFeed.toItemTimeLineUseCase(): ItemTimeLineUseCase {
    return ItemTimeLineUseCase(
        itemFeedTopUseCase = ItemFeedTopUseCase(
            name = name,
            restaurantName = restaurantName,
            rating = rating,
            profilePictureUrl = profilePictureUrl,
            onMenuClickListener = { },
            onProfileImageClickListener = { },
            onNameClickListener = { },
            onRestaurantClickListener = { }
        ),
        itemFeedBottomUseCase = ItemFeedBottomUsecase(
            clickLikeListener = { },
            clickCommentListener = { },
            clickShareListener = { },
            clickFavoriteListener = { },
            likeAmount = 0,
            commentAmount = 0,
            author = "",
            comment = "",
            isLike = true,
            isFavorite = true
        ),
        pageAdapter = FeedPagerAdapter().apply {
            setList(
                reviewImages
            )
        }
    )
}