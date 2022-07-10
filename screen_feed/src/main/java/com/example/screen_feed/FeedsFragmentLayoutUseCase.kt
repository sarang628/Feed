package com.example.screen_feed

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

data class FeedsFragmentLayoutUseCase(
    val onRefreshListener: SwipeRefreshLayout.OnRefreshListener,
    val onMenuItemClickListener: Toolbar.OnMenuItemClickListener
    val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    val reLoad: View.OnClickListener
)