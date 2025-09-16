package com.sarang.torang.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.sarang.torang.compose.feed.FeedScreen
import com.sarang.torang.uistate.FeedUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestFeedScreenAndSnackBar() {
    var errorMsg: String? by remember { mutableStateOf(null) }
    var feedUiState: FeedUiState by remember { mutableStateOf(FeedUiState.Empty) }
    val coroutine = rememberCoroutineScope()

    Box {
        FeedScreen( //Test
            uiState = feedUiState
        )
        Row {
            TextField(
                errorMsg ?: "",
                onValueChange = { errorMsg = it },
                placeholder = { Text("write error message.") })
            Button(onClick = {
                feedUiState = FeedUiState.Error(errorMsg)
            }) { Text("set") }
        }
    }
}