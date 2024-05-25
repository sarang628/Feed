package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun EmptyFeed() {
    Box(Modifier.fillMaxSize())
    {
        Text(text = "피드가 없습니다.", Modifier.align(Alignment.Center))
    }
}