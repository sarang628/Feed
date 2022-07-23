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
            name = "sryang",
            restaurantName = "mcdonalds",
            rating = 4.5f,
            profilePictureUrl = "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/",
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
            likeAmount = 10,
            commentAmount = 20,
            author = "sryang",
            comment = "comment",
            isLike = true,
            isFavorite = true
        ),
        pageAdapter = FeedPagerAdapter().apply {
            setList(
                arrayListOf(
                    "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/",
                    "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/",
                    "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/"
                )
            )
        }
    )
}