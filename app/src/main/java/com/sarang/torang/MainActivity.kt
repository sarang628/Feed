package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.feed.FeedScreenByPictureId
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.compose.feed.PreviewReconnect
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefresh
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
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
    val feedScreenState = rememberFeedScreenState()

    CompositionLocalProvider(
        LocalFeedCompose provides CustomFeedCompose,
        LocalPullToRefreshLayoutType provides customPullToRefresh,
        LocalExpandableTextType provides CustomExpandableTextType,
        LocalFeedImageLoader provides {CustomFeedImageLoader().invoke(it)},
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
    ) {
        NavHost(
            navController = navController,
            startDestination = "FeedScreenInMain"
        ) {
            composable("Menu") {
                Menu(
                    onFeedScreen                = { navController.navigate("FeedScreenTest") },
                    onLoginRepository           = { navController.navigate("LoginRepositoryTest") },
                    onFeedScreenInMain          = { navController.navigate("FeedScreenInMain") },
                    onFeed                      = { navController.navigate("FeedTest") },
                    onFeedScreenByRestaurantId  = { navController.navigate("FeedScreenByRestaurantId") },
                    onFeedScreenByPictureId     = { navController.navigate("FeedScreenByPictureId") },
                    onFeedScreenByReviewId      = { navController.navigate("FeedScreenByReviewId") }
                )
            }
            composable("FeedScreenTest") {
                FeedScreenTest()
            }
            composable("TestPinchZoom") {
                TestPinchZoom()
            }
            composable("FeedReconnect") {
                PreviewReconnect()
            }
            composable("FeedScreenInMain") {
                LaunchedEffect(Unit) { feedScreenState.showSnackBar("FeedScreenInMain") }
                FeedScreenInMainTest()
            }
            composable("LoginRepositoryTest") {
                LoginRepositoryTest(loginRepository = loginRepository)
            }
            composable("FeedScreenByRestaurantId") {
                TestFeedScreenByRestaurantId(234)
            }
            composable ("FeedScreenByPictureId") {
                FeedScreenByPictureId(
                    pictureId = 1272,
                    showLog = true,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("FeedScreenByReviewId") {
                FeedScreenByReviewId(
                    reviewId = 585,
                    onBack = {
                        navController.popBackStack()
                    },
                    showLog = true
                )
            }
        }
    }
}
// @formatter:on

@OptIn(ExperimentalLayoutApi::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun OperationButtons(
    onTop: () -> Unit = {},
    onLoading: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onEmpty: () -> Unit = {},
    onError: () -> Unit = {},
    onReconnect: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    FlowRow {
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onTop, label = { Text("top") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onLoading,
            label = { Text("loading") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onSuccess,
            label = { Text("success") })
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onEmpty, label = { Text("empty") })
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onError, label = { Text("error") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onReconnect,
            label = { Text("reconnect") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onRefresh,
            label = { Text("refresh") })
    }
}

@Preview
@Composable
fun Menu(
    onFeedScreen: () -> Unit = {},
    onLoginRepository: () -> Unit = {},
    onFeedScreenInMain: () -> Unit = {},
    onFeed: () -> Unit = {},
    onFeedScreenByRestaurantId: () -> Unit = {},
    onFeedScreenByPictureId: () -> Unit = {},
    onFeedScreenByReviewId: () -> Unit = {},
) {
    Column {
        Button(onFeedScreen) { Text("FeedScreen") }
        Button(onLoginRepository) { Text("LoginRepository") }
        Button(onFeedScreenInMain) { Text("FeedScreenInMain") }
        Button(onFeed) { Text("Feed") }
        Button(onFeedScreenByRestaurantId) { Text("FeedScreenByRestaurantId") }
        Button(onFeedScreenByPictureId) { Text("FeedScreenByPictureId") }
        Button(onFeedScreenByReviewId) { Text("FeedScreenByReviewId") }
    }
}