package com.posco.feedscreentestapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.screen_feed.FeedService
import com.example.screen_feed.FeedsScreenInputEvents
import com.example.screen_feed.FeedsViewModel
import com.example.screen_feed.TestFeedsScreen
import com.sryang.torang_repository.datasource.FeedRemoteDataSource
import com.sryang.torang_repository.repository.feed.FeedRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feedsViewModel: FeedsViewModel by viewModels()

//    @Inject
//    lateinit var feedDao: FeedDao

//    @Inject
//    lateinit var pictureDao: PictureDao

    @Inject
    lateinit var feedService: FeedService

    @Inject
    lateinit var feedRepository: FeedRepository

    @Inject
    lateinit var feedRemoteDataSource: FeedRemoteDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(key1 = "", block = {
                feedService.getFeeds(HashMap())
            })

            FeedScreen(feedsViewModel = feedsViewModel)
        }
    }
}

@Composable
fun FeedScreen(
    feedsViewModel: FeedsViewModel,

    ) {
    val context = LocalContext.current
    var isExpandMenuBottomSheet by remember { mutableStateOf(false) }
    var isExpandCommentBottomSheet by remember { mutableStateOf(false) }
    var isShareCommentBottomSheet by remember { mutableStateOf(false) }
    Box() {
        TestFeedsScreen(
            feedsViewModel = feedsViewModel,
            feedsScreenInputEvents = FeedsScreenInputEvents(
                onRefresh = {
                    feedsViewModel.refreshFeed()
                },
                onMenu = {
                    Log.d("MainActivity", "onMenu")
                    isExpandMenuBottomSheet = !isExpandMenuBottomSheet
                },
                onComment = {
                    isExpandCommentBottomSheet = !isExpandCommentBottomSheet
                },
                onShare = {
                    isShareCommentBottomSheet = !isShareCommentBottomSheet
                },
                onProfile = {
                    Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                },
                onRestaurant = {
                    Toast.makeText(context, "preparing..", Toast.LENGTH_SHORT)
                        .show()
                },
                onAddReview = {
                    Toast.makeText(context, "preparing..", Toast.LENGTH_SHORT)
                        .show()
                },
                onFavorite = {
                    Toast.makeText(context, "preparing..", Toast.LENGTH_SHORT)
                        .show()
                },
                onImage = {
                    Toast.makeText(context, "preparing..", Toast.LENGTH_SHORT)
                        .show()
                },
                onLike = {
                    Toast.makeText(context, "preparing..", Toast.LENGTH_SHORT)
                        .show()
                },
                onName = {
                    Toast.makeText(context, "preparing..", Toast.LENGTH_SHORT)
                        .show()
                }
            ),
            imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
            profileImageServerUrl = "http://sarang628.iptime.org:89/",
            isExpandMenuBottomSheet = isExpandMenuBottomSheet,
            isExpandCommentBottomSheet = isExpandCommentBottomSheet,
            isShareCommentBottomSheet = isShareCommentBottomSheet
        )
    }
}