package com.example.screen_feed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.screen_feed.R

@Composable
fun ReactionBar(
    onLikeClickListener: ((Int) -> Unit)? = null,
    onCommentClickListener: ((Int) -> Unit)? = null,
    onShareClickListener: ((Int) -> Unit)? = null,
    onClickFavoriteListener: ((Int) -> Unit)? = null,
) {
    Row() {
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Image(
            painter = painterResource(id = R.drawable.b3s),
            contentDescription = "",
            modifier = Modifier.size(25.dp).clickable {
                onLikeClickListener?.invoke(0)
            }
        )
        Spacer(modifier = Modifier.padding(start = 12.dp))
        Image(
            painter = painterResource(id = R.drawable.chat),
            contentDescription = "",
            modifier = Modifier.size(25.dp)
                .clickable {
                    onCommentClickListener?.invoke(0)
                }
        )
        Spacer(modifier = Modifier.padding(start = 12.dp))
        Image(
            painter = painterResource(id = R.drawable.message),
            contentDescription = "",
            modifier = Modifier.size(25.dp).clickable {
                onShareClickListener?.invoke(0)
            }
        )

        Text(text = "", modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = "",
            modifier = Modifier.size(25.dp).clickable {
                onClickFavoriteListener?.invoke(0)
            }
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
    }
}