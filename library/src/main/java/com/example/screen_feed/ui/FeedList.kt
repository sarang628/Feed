package com.example.screen_feed.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.data.Feed
import com.example.screen_feed.uistate.FeedUiState

@Composable
fun FeedList(
    clickProfile: ((Int) -> Unit)? = null,
    list: List<Feed>?,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    onMenuClickListener: ((Int) -> Unit)? = null,
    onNameClickListener: ((Int) -> Unit)? = null,
    onRestaurantClickListener: ((Int) -> Unit)? = null,
    onLikeClickListener: ((Int) -> Unit)? = null,
    onCommentClickListener: ((Int) -> Unit)? = null,
    onShareClickListener: ((Int) -> Unit)? = null,
    onClickFavoriteListener: ((Int) -> Unit)? = null
) {
    LazyColumn() {
        list?.let {
            items(list.size) {
                ItemFeed(
                    list[it].FeedUiState(
                        clickProfile = clickProfile,
                        clickImage = clickImage,
                        onMenuClickListener = onMenuClickListener,
                        onNameClickListener = onNameClickListener,
                        onRestaurantClickListener = onRestaurantClickListener,
                        onShareClickListener = onShareClickListener,
                        onLikeClickListener = onLikeClickListener,
                        onCommentClickListener = onCommentClickListener,
                        onClickFavoriteListener = onClickFavoriteListener
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreViewFeedList() {
    val list = JsonToObjectGenerator<Feed>().getListByFile(LocalContext.current, "feeds.json", Feed::class.java)
    FeedList(list = list)
}