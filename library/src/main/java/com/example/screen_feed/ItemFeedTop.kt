package com.example.screen_feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.example.screen_feed.uistate.FeedTopUIState

@Composable
fun ItemFeedTop(uiState: FeedTopUIState? = null) {
    if(uiState == null){
        return
    }

    Row(
        Modifier
            .height(Dp(40f))
            .padding(start = Dp(15f))
            .fillMaxSize(), verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = uiState.profilePictureUrl,
            contentDescription = "",
            modifier = Modifier.size(Dp(30f))
        )
        Column(
            Modifier
                .padding(start = Dp(8f))
                .weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = uiState.name?:"")
                Row(Modifier.padding(start = Dp(5f))) {
                    RatingBar(uiState.rating?:0f)
                }
            }
            Text(
                text = uiState.restaurantName?:""
            , color = Color.DarkGray
            )
        }
        menu()
    }
}

@Composable
fun RatingBar(rating : Float) {
    Row() {
        for(f in 0..rating.toInt()){
            Image(
                painter = painterResource(id = R.drawable.selected_heart),
                contentDescription = "",
                Modifier
                    .size(Dp(15f))
                    .padding(start = Dp(2f))
            )
        }
    }
}

@Composable
fun menu() {
    Column(Modifier.padding(end = Dp(10f))) {
        Image(
            painter = painterResource(id = R.drawable.dot),
            contentDescription = "",
            modifier = Modifier.size(Dp(29f))
        )
    }
}

@Preview
@Composable
fun test() {
    ItemFeedTop()
}