package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.screen_feed.FeedService
import com.example.screen_feed.FeedsViewModel
import com.posco.feedscreentestapp.di.feed.FeedScreen
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.dao.PictureDao
import com.sryang.torang_repository.datasource.FeedRemoteDataSource
import com.sryang.torang_repository.repository.feed.FeedRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feedsViewModel: FeedsViewModel by viewModels()

    @Inject
    lateinit var feedDao: FeedDao

    @Inject
    lateinit var pictureDao: PictureDao

    @Inject
    lateinit var feedService: FeedService

    @Inject
    lateinit var feedRepository: FeedRepository

    @Inject
    lateinit var feedRemoteDataSource: FeedRemoteDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FeedScreen(feedsViewModel = feedsViewModel)
        }
    }
}