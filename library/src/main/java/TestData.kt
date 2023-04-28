import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.adapters.FeedPagerAdapter
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.uistate.ItemFeedBottomUIState
import com.example.screen_feed.uistate.ItemFeedTopUIState
import com.example.screen_feed.uistate.ItemFeedUIState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun getTestFeedUiState(lifecycleOwner: LifecycleOwner, context: Context, view: View): StateFlow<FeedsUIstate> {
    val data = MutableStateFlow<FeedsUIstate>(
        FeedsUIstate(
            isRefresh = false,
            isProgess = false,
            isEmptyFeed = false,
            toastMsg = "",
            feedItemUiState = ArrayList(),
            isLogin = true,
            errorMsg = "aa"
        )
    );

    lifecycleOwner.lifecycleScope.launch {
        while (true) {
            data.emit(
                data.value.copy(
                    isRefresh = true,
                    feedItemUiState = ArrayList()
                )
            )
            delay(2000)
            data.emit(
                data.value.copy(
                    isRefresh = false,
                    feedItemUiState = arrayListOf(
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view),
                        testItemTimeLineUiState(context, view)
                    )
                )
            )
            delay(5000)

        }
    }

    return data
}

fun testItemFeedBottomUiState(context: Context, view: View) = ItemFeedBottomUIState(
    reviewId = 0,
    likeAmount = 1,
    commentAmount = 2,
    author = "테스트",
    comment = "4",
    isLike = false,
    isFavorite = true,
    onLikeClickListener = {
        Snackbar.make(context, view, "clickLike", Snackbar.LENGTH_SHORT).show()
    },
    onCommentClickListener = {
        Snackbar.make(context, view, "clickComment", Snackbar.LENGTH_SHORT).show()
    },
    onShareClickListener = {
        Snackbar.make(context, view, "clickShare", Snackbar.LENGTH_SHORT).show()
    },
    onClickFavoriteListener = {
        Snackbar.make(context, view, "clickFavority", Snackbar.LENGTH_SHORT).show()
    },
    visibleLike = true,
    visibleComment = true
)

fun testItemFeedTopUIState(context: Context, view: View) = ItemFeedTopUIState(
    reviewId = 0,
    name = "1",
    restaurantName = "2",
    rating = 3.0f,
    profilePictureUrl = "4",
    onMenuClickListener = {
        Snackbar.make(context, view, "clickMenu", Snackbar.LENGTH_SHORT).show()
    },
    onProfileImageClickListener = {
        Snackbar.make(
            context,
            view,
            "profileClick",
            Snackbar.LENGTH_SHORT
        ).show()
    },
    onNameClickListener = {
        Snackbar.make(context, view, "nameClick", Snackbar.LENGTH_SHORT).show()
    },
    onRestaurantClickListener = {
        Snackbar.make(
            context,
            view,
            "restaurantClick",
            Snackbar.LENGTH_SHORT
        ).show()
    }
)

fun testItemTimeLineUiState(context: Context, view: View) = ItemFeedUIState(
    itemId = 0,
    itemFeedTopUiState = testItemFeedTopUIState(context, view),
    itemFeedBottomUiState = testItemFeedBottomUiState(context, view),
    pageAdapter = FeedPagerAdapter().apply {
        setList(
            arrayListOf(
                "http://sarang628.iptime.org:88/1.png",
                "http://sarang628.iptime.org:88/1.png",
                "http://sarang628.iptime.org:88/1.png"
            )
        )
    },
    visibleReviewImage = true,
    reviewImages = ArrayList(),
    imageClickListener = {
        Snackbar.make(context, view, "imageClick " + it, Snackbar.LENGTH_SHORT).show()
    }
)