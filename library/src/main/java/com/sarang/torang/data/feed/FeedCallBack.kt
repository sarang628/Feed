package com.sarang.torang.data.feed

import android.util.Log

data class FeedCallBack(
    val tag                 : String        = "__FeedCallBack",
    val onRefresh           : () -> Unit    = { Log.i(tag, "onRefresh is not set") },
    val onVideoClick        : (Int) -> Unit = { Log.i(tag, "onVideoClick is not set") },
    val onBottom            : () -> Unit    = { Log.i(tag, "onBottom is not set") },
    val onFocusItemIndex    : (Int) -> Unit = { Log.i(tag, "onFocusItemIndex is not set") },
    val onConnect           : () -> Unit    = { Log.i(tag, "onConnect is not set") },
    val onLike              : (Int) -> Unit = { Log.i(tag, "onLike is not set") },
    val onFavorite          : (Int) -> Unit = { Log.i(tag, "onFavorite is not set") }
)