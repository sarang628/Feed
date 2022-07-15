/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.screen_feed

import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter(value = ["app:addReview"])
fun addReview(
    toolbar: androidx.appcompat.widget.Toolbar,
    listener: View.OnClickListener?
) {
    toolbar.setOnMenuItemClickListener {
        it?.let {
            if (it.itemId == R.id.add_review) {
                listener?.let { l ->
                    l.onClick(null)
                }
            }
        }
        false
    }
}


@BindingAdapter(value = ["app:isLogin"])
fun isLogin(
    toolbar: androidx.appcompat.widget.Toolbar,
    isLogin: Boolean?,
) {
    isLogin?.let {

    }
}

@BindingAdapter(value = ["torang:onRefreshListener"])
fun setOnRefreshListener(
    swipeRefreshLayout: SwipeRefreshLayout,
    onRefreshListener: SwipeRefreshLayout.OnRefreshListener?
) {
    onRefreshListener?.let {
        swipeRefreshLayout.setOnRefreshListener(it)
    }
}

@BindingAdapter(value = ["torang:isRefreshing"])
fun setOnRefreshListener(
    swipeRefreshLayout: SwipeRefreshLayout,
    isRefreshing: Boolean?
) {
    isRefreshing?.let {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }
}

@BindingAdapter(value = ["torang:onMenuItemClickListener"])
fun setOnMenuItemClickListener(
    toolBar: Toolbar,
    onMenuItemClickListener: Toolbar.OnMenuItemClickListener?
) {
    onMenuItemClickListener?.let {
        toolBar.setOnMenuItemClickListener(it)
    }
}

@BindingAdapter(value = ["torang:adapter"])
fun setOnMenuItemClickListener(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?
) {
    adapter?.let {
        recyclerView.adapter = it
    }
}

@BindingAdapter(value = ["torang:select"])
fun setSelect(
    view: View,
    select: Boolean?
) {
    select?.let {
        view.isSelected = it
    }
}

@BindingAdapter("torang:formatText")
fun setText(textView: TextView?, str: String?) {
    textView?.setText(Html.fromHtml(str, 0))
}