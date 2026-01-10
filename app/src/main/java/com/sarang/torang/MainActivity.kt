package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.feed.FeedScreenByPictureId
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.compose.feed.component.PreviewReconnect
import com.sarang.torang.compose.feed.internal.components.type.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.type.LocalFeedImageLoader
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefresh
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.feed.FeedRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sarang.torang.test.FeedListTest
import com.sarang.torang.test.FeedScreenInMainTest
import com.sarang.torang.test.FeedScreenTest
import com.sarang.torang.test.TestFeedScreenByRestaurantId
import com.sarang.torang.test.TestPinchZoom
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var loginRepository: LoginRepository

    @Inject lateinit var feedRepository: FeedRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestFeed(loginRepository)
                }
            }
        }
    }
}

// @formatter:off
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun TestFeed(loginRepository : LoginRepository){
    val navController = rememberNavController()
        NavHost(navController = navController,
                startDestination = "Menu") {
            composable("Menu")                      {
                Menu(
                    onFeedScreen                = { navController.navigate("FeedScreenTest") },
                    onLoginRepository           = { navController.navigate("LoginRepositoryTest") },
                    onFeedScreenInMain          = { navController.navigate("FeedScreenInMain") },
                    onFeedScreenByRestaurantId  = { navController.navigate("FeedScreenByRestaurantId") },
                    onFeedScreenByPictureId     = { navController.navigate("FeedScreenByPictureId") },
                    onFeedScreenByReviewId      = { navController.navigate("FeedScreenByReviewId") }
                )
            }
            composable("FeedScreenTest")            { FeedScreenTest() }
            composable("TestPinchZoom")             { TestPinchZoom() }
            composable("FeedReconnect")             { PreviewReconnect() }
            composable("LoginRepositoryTest")       { LoginRepositoryTest(loginRepository = loginRepository) }
            composable("FeedScreenByRestaurantId")  { TestFeedScreenByRestaurantId(234) }
            composable("FeedScreenInMain")          { FeedScreenInMainTest() }
            composable ("FeedScreenByPictureId")    {
                CompositionLocalProvider(
                    LocalFeedCompose provides CustomFeedCompose,
                    LocalPullToRefreshLayoutType provides customPullToRefresh,
                    LocalExpandableTextType provides CustomExpandableTextType,
                    LocalFeedImageLoader provides {CustomFeedImageLoader().invoke(it)},
                    LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
                ){
                FeedScreenByPictureId(
                    pictureId = 1272,
                    showLog = true,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
                }
            composable("FeedScreenByReviewId")      {
                CompositionLocalProvider(
                    LocalFeedCompose provides CustomFeedCompose,
                    LocalPullToRefreshLayoutType provides customPullToRefresh,
                    LocalExpandableTextType provides CustomExpandableTextType,
                    LocalFeedImageLoader provides {CustomFeedImageLoader().invoke(it)},
                    LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
                ){
                FeedScreenByReviewId(
                    reviewId = 585,
                    onBack = {
                        navController.popBackStack()
                    },
                    showLog = true
                )
            }
        }
            composable("FeedListTest")              { FeedListTest() }
    }
}
// @formatter:on