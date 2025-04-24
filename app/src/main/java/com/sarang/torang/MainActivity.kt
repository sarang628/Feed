package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.FeedRepositoryTest
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.repository.ProfileRepository
import com.sarang.torang.repository.ProfileRepositoryTest
import com.sarang.torang.test.TestBasic
import com.sarang.torang.test.TestFeedScreenAndSnackBar
import com.sarang.torang.test.TestFeedScreenByRestaurantId
import com.sarang.torang.test.TestFeedScreenForMain
import com.sarang.torang.test.TestPinchZoom
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
                    //TestUserFeedByReviewIdScreen_()
                    //TestFeedScreenByReviewId_()
                    //TestFeedScreenByRestaurantId_()
                    TestFeedScreenForMain_()
                    //LoginRepositoryTest_(loginRepository)
                    //ProfileRepositoryTest(profileRepository = profileRepository)
                    //FeedRepositoryTest_(feedRepository = feedRepository)
                    //TestFeedScreenAndSnackBar()
                }
            }
        }
    }
}

// @formatter:off
@Composable fun TestUserFeedByReviewIdScreen_() { TestUserFeedByReviewIdScreen() }
@Composable fun LoginRepositoryTest_(loginRepository: LoginRepository) { LoginRepositoryTest(loginRepository = loginRepository) }
@Composable fun ProfileRepositoryTest_(profileRepository: ProfileRepository) { ProfileRepositoryTest(profileRepository) }
@Composable fun FeedRepositoryTest_(feedRepository: FeedRepository) { FeedRepositoryTest(feedRepository) }
@Composable fun TestFeedScreenByRestaurantId_() { TestFeedScreenByRestaurantId(206) }
@Composable fun TestBasic_() { TestBasic() }
@Composable fun TestPinchZoom_() { TestPinchZoom() }
@Composable fun TestFeedScreenForMain_() { TestFeedScreenForMain() }
// @formatter:on

@Preview
@Composable
fun PreviewFeedScreenForMain() {
    TestFeedScreenForMain(
        pullToRefreshLayout = { _, _, contents -> contents.invoke() },
        feed = { _, _, _, _, _, _, _ ->
        }
    )
}