package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.sarang.torang.compose.feed.feedType

val LocalFeedCompose = compositionLocalOf<feedType> {
    @Composable { _, _, _, _, _, _, _ ->
        Log.w("__LocalFeedCompose", "feed compose isn't set")
    }
}