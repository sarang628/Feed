package com.sarang.torang.compose.feed.viewmodel

interface FeedRefreshable {
    fun refreshFeed()
    fun reconnect()
}