package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
internal fun EmptyFeed() {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Box(Modifier.fillMaxWidth()
                .height(screenHeight.dp))
    {
        Text(text = "피드가 없습니다.", Modifier.align(Alignment.Center))
    }
}