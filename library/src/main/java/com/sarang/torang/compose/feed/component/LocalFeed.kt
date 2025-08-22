package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
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
    Box {
        Column(modifier = Modifier.height(300.dp)) {
            OutlinedCard(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                Column {
                    Text("name")
                    Text("restaurants")
                    Image(modifier = Modifier.fillMaxWidth().height(200.dp) ,painter = painterResource(R.drawable.ic_add), contentDescription = "")
                }
            }
            Text("contents")
        }
    }
}