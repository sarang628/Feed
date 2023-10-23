package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.posco.feedscreentestapp.di.feed.FeedScreen
import com.sryang.torang_repository.repository.LoginRepository
import com.sryang.torang_repository.repository.LoginRepositoryTest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                FeedScreen(
                    clickAddReview = {},
                    profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/",
                    imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                    onRestaurant = {},
                    onName = {},
                    onImage = {},
                    onProfile = {}
                )
                //LoginRepositoryTest(loginRepository = loginRepository)
            }
        }

    }
}