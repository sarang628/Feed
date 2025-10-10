package com.sarang.torang.viewmodels

interface ISnackBarMessage {
    var msgState : List<String>

    fun removeTopErrorMessage() {
        if (msgState.isNotEmpty())
            msgState = msgState.drop(0)
    }
}