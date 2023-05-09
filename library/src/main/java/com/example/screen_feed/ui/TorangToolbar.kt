package com.example.screen_feed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.screen_feed.R
import com.example.screen_feed.data.Feed
import com.example.screen_feed.uistate.FeedUiState
import com.example.screen_feed.uistate.getFeedsByFile

@Preview
@Composable
fun TorangToolbar(
    clickAddReview: ((Void?) -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, end = 12.dp)
            .height(55.dp)
    ) {
        Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        Text(text = "", modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    clickAddReview?.invoke(null)
                }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun FeedList(
    clickProfile : ((Int)->Unit)? = null,
    list : List<Feed>?
) {
    val isRefreshing = false
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { })
    Box(Modifier.pullRefresh(pullRefreshState)) {

        LazyColumn(Modifier.fillMaxSize()) {
            list?.let {
                items(list.size) {
                    ItemFeed(list[it].FeedUiState(
                        clickProfile = clickProfile
                    ))
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


@Preview
@Composable
fun EmptyFeed() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "피드가 없습니다.")
    }
}

@Preview
@Composable
fun RefreshFeed() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { }) {
            Text(text = "갱신")
        }
    }
}

@Preview
@Composable
fun Loading() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}