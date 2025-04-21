package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.image.provideImageLoader
import com.sarang.torang.di.pinchzoom.PinchZoomImageBox
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.FeedRepositoryTest
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.repository.ProfileRepository
import com.sarang.torang.repository.ProfileRepositoryTest
import com.sarang.torang.test.TestFeedScreenForMain
import com.sarang.torang.test.TestUserFeedByReviewIdScreen
import com.sryang.library.pullrefresh.rememberPullToRefreshState
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var profileRepository: ProfileRepository

    @Inject
    lateinit var feedRepository: FeedRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //TestBasic()
                    //TestPinchZoom()
                    //TestUserFeedByReviewIdScreen_()
                    //TestFeedScreenByReviewId()
                    TestFeedScreenByRestaurantId()
                    //LoginRepositoryTest_(loginRepository)
                    //ProfileRepositoryTest(profileRepository = profileRepository)
                    //FeedRepositoryTest_(feedRepository = feedRepository)
                }
            }
        }
    }
}

@Composable
fun TestUserFeedByReviewIdScreen_() {
    TestUserFeedByReviewIdScreen()
}

@Composable
fun LoginRepositoryTest_(loginRepository: LoginRepository) {
    LoginRepositoryTest(loginRepository = loginRepository)
}

@Composable
fun ProfileRepositoryTest_(profileRepository: ProfileRepository) {
    ProfileRepositoryTest(profileRepository)
}

@Composable
fun FeedRepositoryTest_(feedRepository: FeedRepository) {
    FeedRepositoryTest(feedRepository)
}

@Composable
fun TestFeedScreenByRestaurantId() {
    var restaurantId by remember { mutableIntStateOf(206) }
    Box(Modifier.fillMaxSize()) {
        FeedScreenByRestaurantId(
            restaurantId = restaurantId,
            shimmerBrush = { shimmerBrush(it) },
            feed = provideFeed(),
            bottomDetectingLazyColumn = provideBottomDetectingLazyColumn(),
            pullToRefreshLayout = providePullToRefresh(rememberPullToRefreshState())
        )
        SetRestaurantIdAssistChip(
            modifier = Modifier.align(Alignment.TopEnd),
            restaurantId = restaurantId,
            { restaurantId = it }
        )
    }
}

@Composable
fun TestFeedScreenByReviewId() {
    var reviewId by remember { mutableIntStateOf(0) }
    Box(Modifier.fillMaxSize()) {
        FeedScreenByReviewId(
            reviewId = reviewId,
            shimmerBrush = { shimmerBrush(it) },
            feed = provideFeed(),
            bottomDetectingLazyColumn = provideBottomDetectingLazyColumn(),
            pullToRefreshLayout = providePullToRefresh(rememberPullToRefreshState())
        )
        SetReviewIdAssistChip(modifier = Modifier.align(Alignment.TopEnd), reviewId) {
            reviewId = it
        }
    }
}

@Composable
fun TestBasic() {
    var restaurantId by remember { mutableStateOf("0") }

    Column {
        AssistChip(onClick = { }, label = { Text(text = "onTop") })
        AssistChip(onClick = { }, label = {
            Text(text = "restaurantId:")
            BasicTextField(value = restaurantId, onValueChange = {
                try {
                    restaurantId = it
                } catch (e: Exception) {

                }
            })
        })
    }
}

@Composable
fun SetReviewIdAssistChip(modifier: Modifier = Modifier, reviewId: Int, onReviewId: (Int) -> Unit) {
    AssistChip(modifier = modifier, onClick = { }, label = {
        Text(text = "reviewId:")
        BasicTextField(value = reviewId.toString(), onValueChange = {
            try {
                onReviewId.invoke(it.toInt())
            } catch (e: Exception) {

            }
        })
    })
}

@Composable
fun SetRestaurantIdAssistChip(
    modifier: Modifier = Modifier,
    restaurantId: Int,
    onRestaurantId: (Int) -> Unit
) {
    AssistChip(modifier = modifier, onClick = { }, label = {
        Text(text = "restaurantId:")
        BasicTextField(value = restaurantId.toString(), onValueChange = {
            try {
                onRestaurantId.invoke(it.toInt())
            } catch (e: Exception) {

            }
        })
    })
}


@Composable
fun TestPinchZoom() {
    PinchZoomImageBox(provideImageLoader()) { imageLoader, zoomState ->
        TestFeedScreenForMain(imageLoadCompose = { modifier, url, width, height, contentScale, originHeight ->
            imageLoader.invoke(modifier, url, contentScale, originHeight)
        })
    }
}

@Preview
@Composable
fun PreviewFeedScreenForMain() {
    TestFeedScreenForMain(
        pullToRefreshLayout = { _, _, contents -> contents.invoke() },
        feed = { _, _, _, _, _, _ ->
        }
    )
}