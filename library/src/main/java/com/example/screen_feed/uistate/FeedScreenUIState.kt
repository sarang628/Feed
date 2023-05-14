package com.example.screen_feed.uistate

import android.content.Context
import android.view.View
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.data.Feed


/*피드 프레그먼트 UIState*/
data class FeedsScreenUiState(
    val isRefreshing: Boolean = false, // 스와이프 리프레시 레이아웃 갱신
    val isProgess: Boolean = false, // 로딩 시 프로그레스 바
    val isEmptyFeed: Boolean = false, // 피드가 비어있을 경우
    val isFailedConnection: Boolean = false, // 네트워크 접속에 실패했을 경우
    val isLogin: Boolean = false, // 로그인 여부
    val reLoad: View.OnClickListener? = null, // 갱신 아답터
    val feeds: ArrayList<Feed>? = null, //피드 리스트
    val snackBar: String? = null // 스낵바 표시
)

fun FeedsScreenUiState.isVisibleRefreshButton(): Boolean {
    if (isRefreshing) return false

    return this.isFailedConnection
}


//-------------------------------------------------------------------------------------------

fun testRefreshingOn(): FeedsScreenUiState {
    return FeedsScreenUiState(isRefreshing = true)
}

fun testRefreshingOff(): FeedsScreenUiState {
    return FeedsScreenUiState(isRefreshing = false)
}

fun testProgressOn(): FeedsScreenUiState {
    return FeedsScreenUiState(isProgess = true)
}

fun testProgressOff(): FeedsScreenUiState {
    return FeedsScreenUiState(isProgess = false)
}

fun testEmptyFeedOn(): FeedsScreenUiState {
    return FeedsScreenUiState(isEmptyFeed = true)
}

fun testEmptyFeedOff(): FeedsScreenUiState {
    return FeedsScreenUiState(isEmptyFeed = false)
}

fun testFailedConnectionOn(): FeedsScreenUiState {
    return FeedsScreenUiState(isFailedConnection = true)
}

fun testFailedConnectionOff(): FeedsScreenUiState {
    return FeedsScreenUiState(isFailedConnection = false)
}

fun getTestEmptyFeedFragmentUIstate(): FeedsScreenUiState {
    return FeedsScreenUiState(
        isRefreshing = false,
        isProgess = false,
        isLogin = false,
        reLoad = {}
    )
}

fun getTestFeedList(
    context: Context
): FeedsScreenUiState {
    val list = getFeedsByFile(context = context)
    return FeedsScreenUiState(feeds = list)
}

fun getFeedsByFile(context: Context): ArrayList<Feed> {
    var list = JsonToObjectGenerator<Feed>().getListByFile(
        context = context,
        fileName = "feeds.json",
        rawType = Feed::class.java
    )
    return ArrayList<Feed>().apply {
        addAll(list)
    }
}