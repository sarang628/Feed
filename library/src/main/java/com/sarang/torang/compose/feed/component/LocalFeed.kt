package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.sarang.torang.compose.feed.feedType

val LocalFeedCompose = compositionLocalOf<feedType> {
    @Composable { feed, _, _, _, _, _, _ ->
        Log.w("__LocalFeedCompose", "feed compose isn't set")
        Row {
            Text(feed.name)
            Text(feed.restaurantName)
        }
    }
}