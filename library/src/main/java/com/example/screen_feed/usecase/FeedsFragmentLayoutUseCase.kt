package com.example.screen_feed.usecase

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

data class FeedsFragmentLayoutUseCase(
    val onRefreshListener: SwipeRefreshLayout.OnRefreshListener, //스와이프 레이아웃을 리프레시 할 때 호출되는 이벤트
    val onAddReviewClickListener: Toolbar.OnMenuItemClickListener, // 리뷰를 추가 할 때 호출되는 이벤트
    val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, // 리스트 아답터
    val reLoad: View.OnClickListener, // 갱신 아답터
    val isEmptyFeed: Boolean = false, // 갱신 버튼 보여지는 여부
    val isRefreshing: Boolean = false,
    val isProgress: Boolean = false
)