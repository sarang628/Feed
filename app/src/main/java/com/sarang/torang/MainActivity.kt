package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.feed.FeedScreen
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.compose.feed.FeedScreenSuccessPreview
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefresh
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.repository.ProfileRepository
import com.sarang.torang.repository.ProfileRepositoryTest
import com.sarang.torang.repository.test.FeedRepositoryTest
import com.sarang.torang.test.TestBasic
import com.sarang.torang.test.TestFeedScreenByRestaurantId
import com.sarang.torang.test.TestPinchZoom
import com.sarang.torang.test.TestUserFeedByReviewIdScreen
import com.sarang.torang.uistate.FeedUiState
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var loginRepository: LoginRepository
    @Inject lateinit var profileRepository: ProfileRepository
    @Inject lateinit var feedRepository: FeedRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                       TextScreen()
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
@Composable fun TestFeedScreenByRestaurantId_() { TestFeedScreenByRestaurantId(234) }
@Composable fun TestBasic_() { TestBasic() }
@Composable fun TestPinchZoom_() { TestPinchZoom() }
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun TextScreen(){
    val navController = rememberNavController()
    val feedScreenState = rememberFeedScreenState()
    val scope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalFeedCompose provides CustomFeedCompose,
        LocalPullToRefreshLayoutType provides customPullToRefresh,
        LocalExpandableTextType provides CustomExpandableTextType,
        LocalFeedImageLoader provides CustomFeedImageLoader,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType
    ) {
        NavHost(
            navController = navController,
            startDestination = "feedScreen"
        ) {
            composable("Menu") {
                Menu(onFeedScreen = {navController.navigate("feedScreen")})
            }
            //TestBasic()
            composable("feedScreen") {
                var uiState : FeedUiState by remember { mutableStateOf(FeedUiState.Loading) }
                BottomSheetScaffold(
                    sheetContent = {
                        OperationButtons(
                            onTop = {
                            scope.launch { feedScreenState.onTop() }
                            scope.launch { feedScreenState.showSnackBar("click on top") }
                                    },
                            onLoading = { uiState = FeedUiState.Loading },
                            onSuccess = { uiState = FeedUiState.Success(listOf(Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample,Feed.Sample)) }

                        )
                    }) {
                    FeedScreen(uiState = uiState, feedScreenState = feedScreenState)
                }
            }
            //TestPinchZoom()
            //FeedScreenEmptyPreview()
            //FeedScreenLoadingPreview()
            //TestUserFeedByReviewIdScreen_()
            //TestFeedScreenByReviewId_()
            //TestFeedScreenByRestaurantId_()
            composable("FeedScreenInMain") {
                LaunchedEffect("") { feedScreenState.showSnackBar("FeedScreenInMain") }
                FeedScreenInMain(feedScreenState = feedScreenState)
            }
            //LoginRepositoryTest_(loginRepository)
            //ProfileRepositoryTest(profileRepository = profileRepository)
            //FeedRepositoryTest_(feedRepository = feedRepository)
            //TestFeedScreenAndSnackBar()
        }
    }
}
// @formatter:on

@Preview
@Composable
fun OperationButtons(
    onTop: () -> Unit = {},
    onLoading: () -> Unit = {},
    onSuccess: () -> Unit = {},
){
    Row {
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onTop, label = { Text("top") })
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onLoading, label = { Text("loading") })
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onSuccess, label = { Text("success") })
    }
}

@Preview
@Composable
fun Menu(onFeedScreen: () -> Unit = {}) {
    Button(onFeedScreen) {
        Text("FeedScreen")
    }
}

@Preview
@Composable
fun FeedScreenSuccessPreview1() {
    CompositionLocalProvider(
        LocalFeedCompose provides CustomFeedCompose,
        LocalPullToRefreshLayoutType provides customPullToRefresh,
        LocalExpandableTextType provides CustomExpandableTextType,
        LocalFeedImageLoader provides CustomFeedImageLoader
    ) {
        FeedScreenSuccessPreview()
    }
}