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

import android.view.View
import android.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.torang_core.data.model.Feed

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