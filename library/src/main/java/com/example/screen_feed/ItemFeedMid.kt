package com.example.screen_feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemFeedMid(img: List<String>?) {
    val pagerState = rememberPagerState(0)
    if (img == null)
        return
    Column {
        PagerIndicator(pagerState = pagerState, img = img)
        FeedPager(pagerState = pagerState, img = img)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedPager(
    pagerState: PagerState, img: List<String>?
) {
    if (img == null)
        return

    HorizontalPager(
        pageCount = img.size,
        state = pagerState,
    ) { page ->
        // Our page content
        AsyncImage(
            model = img.get(page),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 10.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    img: List<String>?, pagerState: PagerState
) {
    if (img == null)
        return

    Row(
        Modifier
            .height(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(img.size) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(5.dp)

            )
        }
    }
}

@Preview
@Composable
fun testItemFeedMid() {
    Column() {
        ItemFeedMid(arrayListOf("", "", ""))
    }

}