package com.sarang.torang.compose.feed.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.type.LocalShimmerBrush

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    backgroundColor = 0x000000,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun FeedShimmer(modifier: Modifier = Modifier) {
    val shimmerBrush = rememberShimmerBrush() // ✅ 한 번만 생성
    Box(Modifier.testTag("shimmer").fillMaxSize().background(MaterialTheme.colorScheme.background)){
        Column(modifier = modifier) {
            Box(modifier = Modifier.fillMaxWidth().height(400.dp).clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush))
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(20.dp).clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush))
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(20.dp).clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush))
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(20.dp).clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush))
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush))
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(350.dp).clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush))
        }
    }
}

@Preview
@Composable
fun PreviewFeedShimmer() { FeedShimmer(modifier = Modifier.fillMaxSize()) }