package com.sarang.torang.compose.feed

import androidx.compose.runtime.Composable
import com.sarang.torang.data.feed.Feed

typealias feedType = @Composable (
    feed: Feed,
    onLike: (Int) -> Unit,
    onFavorite: (Int) -> Unit,
    isLogin: Boolean,
    onVideoClick: () -> Unit,
    imageHeight: Int,
    pageScrollAble: Boolean
) -> Unit