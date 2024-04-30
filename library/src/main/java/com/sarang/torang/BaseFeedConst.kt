package com.sarang.torang

import android.util.Log
import com.sarang.torang.BaseFeedConst.TAG

object BaseFeedConst {
    const val TAG = "__BASEFEED"
}
fun baseFeedLog(str: String) {
    Log.d(TAG, str)
}