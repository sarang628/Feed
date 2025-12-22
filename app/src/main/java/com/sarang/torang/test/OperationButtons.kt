package com.sarang.torang.test

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun OperationButtons(
    onTop: () -> Unit = {},
    onLoading: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onEmpty: () -> Unit = {},
    onError: () -> Unit = {},
    onReconnect: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    FlowRow {
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onTop, label = { Text("top") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onLoading,
            label = { Text("loading") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onSuccess,
            label = { Text("success") })
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onEmpty, label = { Text("empty") })
        AssistChip(modifier = Modifier.padding(4.dp), onClick = onError, label = { Text("error") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onReconnect,
            label = { Text("reconnect") })
        AssistChip(
            modifier = Modifier.padding(4.dp),
            onClick = onRefresh,
            label = { Text("refresh") })
    }
}