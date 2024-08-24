package com.sarang.torang

import androidx.compose.runtime.Composable
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
) -> Unit) = { it, onLike, onFavorite, isLogin ->
    Feed(
        review = it.toReview(),
        imageLoadCompose = provideTorangAsyncImage(),
        onMenu = {},
        onLike = { onLike.invoke(it.reviewId) },
        onFavorite = { onFavorite.invoke(it.reviewId) },
        onComment = {},
        onShare = {},
        onProfile = {},
        isZooming = {},
        onName = {},
        onImage = {},
        onRestaurant = {},
        onLikes = {},
        isLogin = isLogin,
        expandableText = provideExpandableText()
    )
}