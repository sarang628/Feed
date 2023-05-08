package com.example.screen_feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.screen_feed.uistate.FeedBottomUIState

@Composable
fun ItemFeedBottom(uiState: FeedBottomUIState?) {
    Column(Modifier.padding()) {
        ReactionBar()
        Spacer(modifier = Modifier.height(8.dp))
        ItemFeedComment(
            contents = uiState?.contents
        , likeAmount = uiState?.likeAmount
        , author = uiState?.author
        , comment = uiState?.comment
        , commentAmount = uiState?.commentAmount
        , author1 = uiState?.author1
        , author2 = uiState?.author2
        , comment1 = uiState?.comment1
        , comment2 = uiState?.comment2
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ReactionBar() {
    Row() {
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Image(
            painter = painterResource(id = R.drawable.b3s),
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.padding(start = 12.dp))
        Image(
            painter = painterResource(id = R.drawable.chat),
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.padding(start = 12.dp))
        Image(
            painter = painterResource(id = R.drawable.message),
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )

        Text(text = "", modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
    }
}

@Composable
fun ItemFeedComment(
    contents: String? = "",
    likeAmount: Int? = 0,
    author: String? = "",
    comment: String? = "",
    commentAmount: Int? = 0,
    author1: String? = "",
    comment1: String? = "",
    author2: String? = "",
    comment2: String? = ""
) {
    Column(Modifier.padding(start = 8.dp)) {
        Text(text = contents ?: "")
        Text(text = "좋아요 $likeAmount 개", color = Color.DarkGray)
        Row() {
            Text(text = author ?: "", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(start = 3.dp))
            Text(text = comment ?: "")
        }
        Text(text = "댓글 $commentAmount 개 모두보기", color = Color.DarkGray)
        Row() {
            Text(text = author1 ?: "", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(start = 3.dp))
            Text(text = comment1 ?: "")
        }
        Row() {
            Text(text = author2 ?: "", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(start = 3.dp))
            Text(text = comment2 ?: "")
        }
    }

}

@Preview
@Composable
fun PreViewItemFeedBottom() {
    Column() {
        ReactionBar()
        ItemFeedComment()
    }
}