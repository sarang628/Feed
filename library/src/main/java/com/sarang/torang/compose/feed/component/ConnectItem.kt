package com.sarang.torang.compose.feed.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ConnectItem(padding : PaddingValues = PaddingValues(0.dp),
                onConnect : ()->Unit = {}){
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Box(Modifier.fillMaxWidth()
                .height(screenHeight.dp)
                .padding(padding)){
        Button(modifier = Modifier.align(Alignment.Center),
               onClick  = onConnect) { Text("connect") }
    }
}