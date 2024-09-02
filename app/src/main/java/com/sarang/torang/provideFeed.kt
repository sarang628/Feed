package com.sarang.torang

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage

fun provideFeed(): @Composable (
    (
    feed: Feed,
    onLike: (Int) -> Unit,
    onFavorite: (Int) -> Unit,
    isLogin: Boolean,
    onVideoClick: () -> Unit,
    imageHeight: Int,
) -> Unit) = { feed, onLike, onFavorite, isLogin, onVideoClick, imageHeight ->
    Feed(
        review = feed.toReview(),
        imageLoadCompose = provideTorangAsyncImage(),
        onMenu = {},
        onLike = { onLike.invoke(feed.reviewId) },
        onFavorite = { onFavorite.invoke(feed.reviewId) },
        onComment = {},
        onShare = {},
        onProfile = {},
        isZooming = {},
        onName = {},
        onImage = {},
        onRestaurant = {},
        onLikes = {},
        isLogin = isLogin,
        expandableText = provideExpandableText(),
        videoPlayer = {
            VideoPlayerScreen(
                videoUrl = it,
                isPlaying = feed.isPlaying,
                onClick = onVideoClick,
                onPlay = {}
            )
        },
        imageHeight = if (imageHeight != 0) imageHeight.dp else 400.dp
    )
}