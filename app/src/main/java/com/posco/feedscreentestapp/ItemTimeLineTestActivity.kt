package com.posco.feedscreentestapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ItemTimeLineTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding = DataBindingUtil.setContentView<ItemTimeLineBinding>(
//            this,
//            com.example.screen_feed.R.layout.item_time_line
//        )

/*        val useCase = ItemTimeLineUseCase(
            itemFeedTopUseCase = ItemFeedTopUseCase(
                name = "sryang",
                restaurantName = "mcdonalds",
                rating = 4.5f,
                profilePictureUrl = "https://thumb.mt.co.kr/06/2022/01/2022011414312292328_1.jpg/dims/optimize/",
                onMenuClickListener = { snackBar(binding.root, "clickMenu") },
                onProfileImageClickListener = { snackBar(binding.root, "profileClick") },
                onNameClickListener = { snackBar(binding.root, "nameClick") },
                onRestaurantClickListener = { snackBar(binding.root, "restaurantClick") }
            ),
            itemFeedBottomUseCase = ItemFeedBottomUsecase(
                clickLikeListener = { snackBar(binding.root, "clickLike") },
                clickCommentListener = { snackBar(binding.root, "clickComment") },
                clickShareListener = { snackBar(binding.root, "clickshare") },
                clickFavoriteListener = { snackBar(binding.root, "clickFavorite") },
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

        binding.useCase = useCase*/
    }

    fun snackBar(view: View, text: String) {
        Snackbar.make(
            this, view, text, Snackbar.LENGTH_SHORT
        ).show()
    }
}