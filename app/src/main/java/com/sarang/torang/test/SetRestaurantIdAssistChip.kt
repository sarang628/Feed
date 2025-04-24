package com.sarang.torang.test

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SetRestaurantIdAssistChip(
    modifier: Modifier = Modifier,
    restaurantId: Int,
    onRestaurantId: (Int) -> Unit
) {
    AssistChip(modifier = modifier, onClick = { }, label = {
        Text(text = "restaurantId:")
        BasicTextField(value = restaurantId.toString(), onValueChange = {
            try {
                onRestaurantId.invoke(it.toInt())
            } catch (e: Exception) {

            }
        })
    })
}
