package com.example.screen_feed.adapters

import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.screen_feed.R

@BindingAdapter(value = ["select"])
fun setSelect(
    view: View,
    select: Boolean?
) {
    select?.let {
        view.isSelected = it
    }
}

@BindingAdapter("formatText")
fun setText(textView: TextView?, str: String?) {
    textView?.setText(Html.fromHtml(str, 0))
}