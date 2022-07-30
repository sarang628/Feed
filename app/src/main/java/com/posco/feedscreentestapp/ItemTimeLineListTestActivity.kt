package com.posco.feedscreentestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.posco.feedscreentestapp.databinding.ActivityItemTimeLineListTestBinding

class ItemTimeLineListTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityItemTimeLineListTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val useCase = ItemTimeLineUseCase(
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

        binding.rv.apply {
            adapter = FeedsAdapter().apply {
                setFeeds(
                    arrayListOf(useCase,useCase,useCase)
                )
            }
        }*/

    }

    fun snackBar(view: View, text: String) {
        Snackbar.make(
            this, view, text, Snackbar.LENGTH_SHORT
        ).show()
    }
}