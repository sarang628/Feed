package com.sarang.torang.test

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SetReviewIdAssistChip(modifier: Modifier = Modifier, reviewId: Int, onReviewId: (Int) -> Unit) {
    AssistChip(modifier = modifier, onClick = { }, label = {
        Text(text = "reviewId:")
        BasicTextField(value = reviewId.toString(), onValueChange = {
            try {
                onReviewId.invoke(it.toInt())
            } catch (e: Exception) {

            }
        })
    })
}