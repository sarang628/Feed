package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R

@Preview
@Composable
internal fun TorangToolbar(
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