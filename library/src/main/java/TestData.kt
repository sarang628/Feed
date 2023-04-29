import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.adapters.FeedPagerAdapter
import com.example.screen_feed.uistate.FeedsUIstate
import com.example.screen_feed.uistate.FeedBottomUIState
import com.example.screen_feed.uistate.FeedTopUIState
import com.example.screen_feed.uistate.FeedUiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//피드 하단 테스트 데이터
fun testItemFeedBottomUiState(context: Context, view: View) = FeedBottomUIState(
    reviewId = 0,
    likeAmount = 1,
    commentAmount = 2,
    author = "테스트",
    comment = "4",
    isLike = false,
    isFavorite = false,
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

fun testItemFeedUiState(context: Context, view: View) = FeedUiState(
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

fun getTestFeedUiState(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    view: View
): StateFlow<FeedsUIstate> {
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
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view),
                    testItemFeedUiState(context, view)
                )
            )
        )
    }

    return data
}