package com.sarang.torang.compose.feed.viewmodel

interface ISnackBarMessage {
    var msgState : List<String>

    fun removeTopErrorMessage() {
        if (msgState.isNotEmpty())
            msgState = msgState.drop(0)
    }
}