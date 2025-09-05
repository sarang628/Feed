package com.sarang.torang.compose.feed.component

import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.R
import com.sarang.torang.compose.feed.feedType

val LocalFeedCompose = compositionLocalOf<feedType> {
    @Composable { feed, _, _, _, _, _, _ ->
        Log.w("__LocalFeedCompose", "feed compose isn't set")
        FeedSample()
    }
}

@Composable
@Preview
fun FeedSample() {
    Column {
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Row(Modifier.padding(8.dp)) {
                    Image(modifier = Modifier.size(40.dp).clip(CircleShape) ,painter = painterResource(R.drawable.sample), contentDescription = "", contentScale = ContentScale.Crop)
                    Column {
                        Text("name")
                        Text("restaurants")
                    }
                }
                Image(modifier = Modifier.fillMaxWidth().height(300.dp) ,painter = painterResource(R.drawable.sample), contentDescription = "", contentScale = ContentScale.Crop)
                Text(modifier = Modifier.padding(8.dp), text = "contents")
            }
        }

    }
}