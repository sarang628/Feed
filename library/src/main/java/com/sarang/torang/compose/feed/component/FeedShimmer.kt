package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.type.LocalShimmerBrush

@Composable
internal fun FeedShimmer(modifier: Modifier = Modifier) {
    val showShimmer by remember { mutableStateOf(true) }
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(8.dp)).background(
            LocalShimmerBrush.current.invoke(showShimmer)))
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(8.dp)).background(
            LocalShimmerBrush.current.invoke(showShimmer)))
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(8.dp)).background(
            LocalShimmerBrush.current.invoke(showShimmer)))
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(8.dp)).background(
            LocalShimmerBrush.current.invoke(showShimmer)))
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(8.dp)).background(
            LocalShimmerBrush.current.invoke(showShimmer)))
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(8.dp)).background(
            LocalShimmerBrush.current.invoke(showShimmer)))
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(8.dp)).background(
            LocalShimmerBrush.current.invoke(showShimmer)))
    }
}

@Preview
@Composable
fun PreviewFeedShimmer() { FeedShimmer(modifier = Modifier.fillMaxSize()) }