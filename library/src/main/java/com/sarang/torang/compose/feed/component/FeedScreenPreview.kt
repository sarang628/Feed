package com.sarang.torang.compose.feed.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.uistate.FeedLoadingUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Loading
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenEmptyPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Empty
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenSuccessPreview() {
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Success
    )
}

@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun TransitionPreview(){
    var uiState : FeedLoadingUiState by remember { mutableStateOf(FeedLoadingUiState.Loading) }

    LaunchedEffect(uiState) {
        delay(1000)
        uiState = FeedLoadingUiState.Success
    }

    FeedScreen(/*Preview*/
        loadingUiState = uiState
    )
}

@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun PreviewReconnect(){
    FeedScreen(/*Preview*/
        loadingUiState = FeedLoadingUiState.Reconnect
    )
}