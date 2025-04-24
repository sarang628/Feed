package com.sarang.torang.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TestBasic() {
    var restaurantId by remember { mutableStateOf("0") }

    Column {
        AssistChip(onClick = { }, label = { Text(text = "onTop") })
        AssistChip(onClick = { }, label = {
            Text(text = "restaurantId:")
            BasicTextField(value = restaurantId, onValueChange = {
                try {
                    restaurantId = it
                } catch (e: Exception) {

                }
            })
        })
    }
}