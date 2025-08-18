package com.sarang.torang.compose.feed.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias FeedTopAppBarType = @Composable () -> Unit

val LocalFeedTopAppBarType = compositionLocalOf<FeedTopAppBarType> {
    @Composable {
        Log.i("__LocalFeedTopAppBarType", "topAppBar is not set")
    }
}