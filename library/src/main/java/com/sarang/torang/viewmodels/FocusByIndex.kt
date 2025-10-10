package com.sarang.torang.viewmodels

interface FocusByIndex {
    var focusedIndexState: Int
    fun onFocusItemIndex(index: Int) { focusedIndexState = index }
}