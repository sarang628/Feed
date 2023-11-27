package com.sryang.torang.di.feed_di

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.sryang.torang.di.feed_di.FeedScreen
import com.sryang.torang.BuildConfig.*

@Composable
fun ProvideFeedScreen(navController: NavHostController) {
    FeedScreen(
        onProfile = { navController.navigate("profile/$it") },
        onRestaurant = { navController.navigate("restaurant/$it") },
        onImage = {},
        onName = {},
        clickAddReview = { navController.navigate("addReview") },
        profileImageServerUrl = PROFILE_IMAGE_SERVER_URL,
        imageServerUrl = REVIEW_IMAGE_SERVER_URL,
        ratingBar = {}
    )
}