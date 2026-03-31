package com.sarang.torang.compose.feed.viewmodel

interface FocusByIndex {
    var focusedIndexState: Int
    fun onFocusItemIndex(index: Int) { focusedIndexState = index }
}