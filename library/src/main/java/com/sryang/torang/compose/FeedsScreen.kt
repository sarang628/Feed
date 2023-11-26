package com.sryang.torang.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.torang.R
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.viewmodels.FeedsViewModel

/**
 * DI 모듈에서 제공하는 FeedScreen을 사용해주세요
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedsScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    feeds: @Composable () -> Unit,
    errorComponent: @Composable () -> Unit,
    feedMenuBottomSheetDialog: @Composable (Boolean) -> Unit,
    commentBottomSheetDialog: @Composable (Boolean) -> Unit,
    shareBottomSheetDialog: @Composable (Boolean) -> Unit,
    reportDialog: @Composable () -> Unit,
    onAddReview: () -> Unit
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
            actions = {
                Image(painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onAddReview.invoke()
                        })
            })
    }) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            Column { // 타이틀과 추가버튼이 있는 툴바
                errorComponent.invoke()
                Box { // 피드와 스와이프 리프레시
                    feeds.invoke()
                    errorComponent.invoke()
                }
            }
            if (uiState.showMenu) feedMenuBottomSheetDialog.invoke(true)
            if (uiState.showComment) commentBottomSheetDialog.invoke(true)
            if (uiState.showShare) shareBottomSheetDialog.invoke(true)
            if (uiState.showReport && uiState.selectedReviewId != null) reportDialog.invoke()
            uiState.error?.let {
                AlertDialog(
                    onDismissRequest = { feedsViewModel.removeErrorMsg() },
                    confirmButton = {
                        Button(onClick = { feedsViewModel.removeErrorMsg() }) {
                            Text(text = "확인", color = Color.White)
                        }
                    },
                    title = { Text(text = it) })
            }
        }
    }
}