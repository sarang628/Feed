package com.example.screen_feed.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.screen_feed.uistate.FeedBottomUIState

@Composable
fun ItemFeedBottom(uiState: FeedBottomUIState?) {
    Column(Modifier.padding()) {
        ReactionBar(
            onClickFavoriteListener = uiState?.onClickFavoriteListener,
            onCommentClickListener = uiState?.onCommentClickListener,
            onLikeClickListener = uiState?.onLikeClickListener,
            onShareClickListener = uiState?.onShareClickListener
        )
        Spacer(modifier = Modifier.height(8.dp))
        ItemFeedComment(
            contents = uiState?.contents,
            likeAmount = uiState?.likeAmount,
            author = uiState?.author,
            comment = uiState?.comment,
            commentAmount = uiState?.commentAmount,
            author1 = uiState?.author1,
            author2 = uiState?.author2,
            comment1 = uiState?.comment1,
            comment2 = uiState?.comment2
        )
        Spacer(modifier = Modifier.height(12.dp))
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
        if (contents != null)
            Text(text = contents)

        if (likeAmount != null) {
            if (likeAmount > 0)
                Text(text = "좋아요 $likeAmount 개", color = Color.DarkGray)
        }

        if (author != null)
            Row() {
                Text(text = author, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(start = 3.dp))
                Text(text = comment ?: "")
            }

        if (commentAmount != null) {
            if (commentAmount > 0)
                Text(text = "댓글 $commentAmount 개 모두보기", color = Color.DarkGray)
        }

        if (author1 != null)
            Row() {
                Text(text = author1, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(start = 3.dp))
                Text(text = comment1 ?: "")
            }

        if (author2 != null)
            Row() {
                Text(text = author2, fontWeight = FontWeight.Bold)
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