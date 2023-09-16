package com.posco.feedscreentestapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.FeedsScreenInputEvents
import com.example.screen_feed.FeedsViewModel
import com.example.screen_feed.TestFeedsScreen
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.dao.PictureDao
import com.sryang.torang_repository.data.entity.FeedEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var feedsViewModel: FeedsViewModel

    @Inject
    lateinit var feedDao: FeedDao

    @Inject
    lateinit var pictureDao: PictureDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isExpandMenuBottomSheet by remember { mutableStateOf(false) }
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
                        }
                    ),
                    imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                    profileImageServerUrl = "http://sarang628.iptime.org:89/",
                    isExpandMenuBottomSheet = isExpandMenuBottomSheet
                )
            }
        }
    }
}

class TestViewModel @Inject constructor(
    val feedDao: FeedDao
) : ViewModel() {
    fun test() {
        viewModelScope.launch {
            feedDao.insertAll(
                ArrayList<FeedEntity>().apply {
                    add(
                        FeedEntity(
                            review_id = 0,
                            userId = 0
                        )
                    )
                }
            )
        }
    }

    init {
    }

}