package com.example.screen_feed

import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class FeedsFragmentNavigation @Inject constructor() : FeedRvAdtNavigation {
    fun test(context: Context) {
        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
    }

    override fun goProfile(context: Context) {
        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
    }
}

interface FeedRvAdtNavigation {
    fun goProfile(context: Context)
}