package com.sarang.torang.compose.feed.component

enum class RefreshIndicatorState(msg: String) {
    Default("Default"),
    PullingDown("PullingDown"),
    ReachedThreshold("ReachedThreshold"),
    Refreshing("Refreshing")
}