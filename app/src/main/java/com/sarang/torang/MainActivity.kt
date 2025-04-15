package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.di.image.PinchZoomImageBox
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.ProfileRepository
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
            var reviewId by remember { mutableStateOf("245") }
            var restaurantId by remember { mutableStateOf("0") }

            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Column(Modifier.verticalScroll(rememberScrollState())) {
                    /*Column {
                        AssistChip(onClick = { *//*TODO*//* }, label = {
                                Text(text = "MyFeed reviewId:")
                                BasicTextField2(value = reviewId, onValueChange = {
                                    try {
                                        reviewId = it
                                    } catch (e: Exception) {

                                    }
                                })
                            })
                            AssistChip(onClick = { onTop = true }, label = { Text(text = "onTop") })
                            AssistChip(onClick = { *//*TODO*//* }, label = {
                                Text(text = "restaurantId:")
                                BasicTextField2(value = restaurantId, onValueChange = {
                                    try {
                                        restaurantId = it
                                    } catch (e: Exception) {

                                    }
                                })
                            })
                        }*/
                    Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp - 30).dp)) {

                        PinchZoomImageBox {
                            FeedScreenForMain(imageLoadCompose = it)
                        }

                        //MyFeedScreen(reviewId)
                        //FeedScreenByReviewId(reviewId)
                        //FeedScreenByRestaurantId(restaurantId = 0)
                    }
                    //LoginRepositoryTest(loginRepository = loginRepository)
                    //ProfileRepositoryTest(profileRepository = profileRepository)
                    //FeedRepositoryTest(feedRepository = feedRepository)
                    //}
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFeedScreenForMain() {
    FeedScreenForMain(
        pullToRefreshLayout = { _, _, contents -> contents.invoke() },
        feed = { _, _, _, _, _, _ ->
        }
    )
}