package com.sarang.torang.test

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.EmptyTestActivity
import com.sarang.torang.di.feed_di.provideFeedScreenInMain
import com.sarang.torang.viewmodels.FeedScreenInMainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FeedScreenInMainTest() {
    val feedsViewModel: FeedScreenInMainViewModel = hiltViewModel()
    val context : Context = LocalContext.current
    val state = rememberBottomSheetScaffoldState()
    val coroutine = rememberCoroutineScope()
    var testText by remember { mutableStateOf("") }
    BottomSheetScaffold(
        scaffoldState = state,
        sheetPeekHeight = 0.dp,
        sheetContent = { Box{
            FlowRow {
                AssistChip(onClick = {feedsViewModel.subScribeFeed()},
                           label = { Text("subScribeFeed") })
                AssistChip(onClick = {feedsViewModel.loadPage()},
                           label = { Text("loadPage") })
                AssistChip(onClick = {feedsViewModel.deleteAllFeed()},
                           label = { Text("deleteAllFeed") })
                AssistChip(onClick = {
                        context.startActivity(
                            Intent(context,
                                EmptyTestActivity::class.java)) },
                    label = { Text("newActivity") }
                )
                TextField(value = testText, onValueChange = { testText = it })
                    } } } ) {
        Box(modifier = Modifier.fillMaxSize()){
            provideFeedScreenInMain(feedsViewModel)
            FloatingActionButton(modifier = Modifier.align(Alignment.BottomEnd)
                                                    .padding(end = 20.dp, bottom = 20.dp),
                                 onClick = { coroutine.launch { state.bottomSheetState.expand() } }){
                Icon(Icons.AutoMirrored.Default.List, null)
            }
        }
    }
}