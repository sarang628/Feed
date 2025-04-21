package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.image.provideImageLoader
import com.sarang.torang.di.pinchzoom.PinchZoomImageBox
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.FeedRepositoryTest
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.repository.ProfileRepository
import com.sarang.torang.repository.ProfileRepositoryTest
import com.sarang.torang.test.TestFeedScreenForMain
import com.sarang.torang.test.TestUserFeedByReviewIdScreen
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
                    TestUserFeedByReviewIdScreen_()
                    //TestFeedScreenByReviewId()
                    //FeedScreenByRestaurantId_()
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
fun FeedRepositoryTest_(feedRepository: FeedRepository){
    FeedRepositoryTest(feedRepository)
}

@Composable
fun FeedScreenByRestaurantId_() {
    FeedScreenByRestaurantId(
        restaurantId = 0,
        shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(),
        bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()
    )
}

@Composable
fun TestFeedScreenByReviewId() {
    FeedScreenByReviewId(
        shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(),
        bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()
    )
}

@Composable
fun TestBasic() {
    var restaurantId by remember { mutableStateOf("0") }
    var reviewId by remember { mutableStateOf("245") }

    Column {
        AssistChip(onClick = { }, label = {
            Text(text = "MyFeed reviewId:")
            BasicTextField(value = reviewId, onValueChange = {
                try {
                    reviewId = it
                } catch (e: Exception) {

                }
            })
        })
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