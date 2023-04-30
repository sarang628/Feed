import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.uistate.FeedFragmentUIstate
import com.example.screen_feed.uistate.testItemFeedUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


fun getTestFeedFragmentUIstate(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    view: View
): StateFlow<FeedFragmentUIstate> {
    val data = MutableStateFlow(
        FeedFragmentUIstate(
            isRefresh = false,
            isProgess = false,
            feedItemUiState = null,
            isLogin = false,
            reLoad = {},
            onAddReviewClickListener = { false },
            onRefreshListener = {}
        )
    );

    lifecycleOwner.lifecycleScope.launch {
        data.emit(
            data.value.copy(
                isRefresh = true,
                feedItemUiState = null
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