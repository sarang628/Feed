package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FeedTopAppBar(
    onAddReview: () -> Unit = { Log.w("__FeedTopAppBar", "onAddReview is not implemented") },
    onAlarm: () -> Unit = { Log.w("__FeedTopAppBar", "onAlarm is not implemented") },
    topAppIcon: ImageVector = Icons.AutoMirrored.Default.Send,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
) {
    TopAppBar(
        title = { Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
        actions = {
            IconButton(onClick = { onAddReview.invoke() }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "",
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = { onAddReview.invoke() }) {
                Icon(
                    imageVector = topAppIcon,
                    contentDescription = "",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}