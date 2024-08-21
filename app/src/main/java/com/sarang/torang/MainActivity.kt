package com.sarang.torang

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.FeedScreenForMain
import com.sarang.torang.di.feed_di.ShimmerBrushPreview
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.FeedRepositoryTest
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.ProfileRepository
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
        window.requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar()?.hide();

        setContent {
            var reviewId by remember { mutableStateOf("0") }
            var restaurantId by remember { mutableStateOf("0") }
            var onTop by remember { mutableStateOf(false) }
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
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
                            FeedScreenForMain(
                                onAddReview = {},
                                onTop = onTop,
                                shimmerBrush = { shimmerBrush(it) },
                                consumeOnTop = { onTop = false },
                                feed = { it, onLike, onFavorite ->
                                    Feed(
                                        review = it.toReview(),
                                        imageLoadCompose = provideTorangAsyncImage(),
                                        onMenu = {},
                                        onLike = { onLike.invoke(it.reviewId) },
                                        onFavorite = { onFavorite.invoke(it.reviewId) },
                                        onComment = {},
                                        onShare = {},
                                        onProfile = {},
                                        isZooming = {},
                                        onName = {},
                                        onImage = {},
                                        onRestaurant = {},
                                        onLikes = {}
                                    )
                                }
                            )
                            /*ProvideMyFeedScreen(
                                reviewId = try { Integer.parseInt(reviewId) } catch (e: Exception) { 0 }
                            )*/

//                            FeedScreenByRestaurantId(
                            /*FeedScreenByReviewId(
                                reviewId =
                                try {
                                    restaurantId.toInt()
                                } catch (e: Exception) {
                                    0
                                },
                                feed = { it, onLike, onFavorite ->
                                    Feed(
                                        review = it.toReview(),
                                        image = provideTorangAsyncImage(),
                                        onMenu = {},
                                        onLike = { onLike.invoke(it.reviewId) },
                                        onFavorite = { onFavorite.invoke(it.reviewId) },
                                        onComment = {},
                                        onShare = {},
                                        onProfile = {},
                                        isZooming = {},
                                        onName = {},
                                        onImage = {},
                                        onRestaurant = {}
                                    )
                                }
                            )
                        }*/
//                            LoginRepositoryTest(loginRepository = loginRepository)
//                            ProfileRepositoryTest(profileRepository = profileRepository)
//                            FeedRepositoryTest(feedRepository = feedRepository)
                        }
                    }
                }
            }
        }
    }
}