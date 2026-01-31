package com.sarang.torang

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun Menu(navController: NavController = rememberNavController()) {
    Column {
        Button({ navController.navigate("FeedScreenTest") }) { Text("FeedScreen") }
        Button({ navController.navigate("FeedScreenInMain") }) { Text("FeedScreenInMain") }
        Button({ navController.navigate("FeedScreenByRestaurantId") }) { Text("FeedScreenByRestaurantId") }
        Button({ navController.navigate("FeedScreenByPictureId") }) { Text("FeedScreenByPictureId") }
        Button({ navController.navigate("FeedScreenByReviewId") }) { Text("FeedScreenByReviewId") }
        Button({ navController.navigate("LoginRepositoryTest") }) { Text("LoginRepository") }
    }
}