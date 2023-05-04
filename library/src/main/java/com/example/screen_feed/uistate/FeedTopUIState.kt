package com.example.screen_feed.uistate

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

/*피드 상단 UIState*/
data class FeedTopUIState(
    val reviewId: Int = 0,
    val name: String = "",
    val restaurantName: String = "",
    val rating: Float = 0.0f,
    val profilePictureUrl: String? = null,
    val onMenuClickListener: ((Int) -> Unit)? = null,
    val onProfileImageClickListener: ((Int) -> Unit)? = null,
    val onNameClickListener: ((Int) -> Unit)? = null,
    val onRestaurantClickListener: ((Int) -> Unit)? = null
)

//피드 상단 테스트 데이터
fun testItemFeedTopUIState(context: Context, view: View) = FeedTopUIState(
    reviewId = 0,
    name = "루피",
    restaurantName = "맥도날드",
    rating = 3.0f,
    profilePictureUrl = "4",
    onMenuClickListener = {
        Snackbar.make(context, view, "clickMenu", Snackbar.LENGTH_SHORT).show()
    },
    onProfileImageClickListener = {
        Snackbar.make(context, view, "profileClick", Snackbar.LENGTH_SHORT).show()
    },
    onNameClickListener = {
        Snackbar.make(context, view, "nameClick", Snackbar.LENGTH_SHORT).show()
    },
    onRestaurantClickListener = {
        Snackbar.make(context, view, "restaurantClick", Snackbar.LENGTH_SHORT).show()
    }
)
