package com.example.screen_feed.uistate

import android.content.Context
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.data.Feed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/*피드 프레그먼트 UIState*/
data class FeedFragmentUIstate(
    // 스와이프 리프레시 레이아웃 갱신
    val isRefreshing: Boolean = false,
    // 로딩 시 프로그레스 바
    val isProgess: Boolean = false,
    // 피드가 비어있을 경우
    val isEmptyFeed: Boolean = false,
    // 네트워크 접속에 실패했을 경우
    val isFailedConnection: Boolean = false,
    // 로그인 여부
    val isLogin: Boolean = false,
    val onRefreshListener: SwipeRefreshLayout.OnRefreshListener? = null, //스와이프 레이아웃을 리프레시 할 때 호출되는 이벤트
    val onAddReviewClickListener: Toolbar.OnMenuItemClickListener? = null, // 리뷰를 추가 할 때 호출되는 이벤트
    val reLoad: View.OnClickListener? = null, // 갱신 아답터
    val feeds: ArrayList<Feed>? = null,
)

fun FeedFragmentUIstate.isVisibleRefreshButton(): Boolean {
    if (isRefreshing) return false

    return this.isFailedConnection
}


//-------------------------------------------------------------------------------------------
fun getTestSenarioFeedFragmentUIstate(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    view: View
): StateFlow<FeedFragmentUIstate> {
    val data = MutableStateFlow(getTestEmptyFeedFragmentUIstate())
    val delayCount = 1000L
    lifecycleOwner.lifecycleScope.launch {
        while (true) {
            // 스와이프 리프레시 테스트
//            data.emit(testRefreshingOn()); delay(delayCount); data.emit(testRefreshingOff()); delay(delayCount)
            // 프로그레스 테스트
//            data.emit(testProgressOn()); delay(delayCount); data.emit(testProgressOff()); delay(delayCount)
            // 비어있는 피드 테스트
//            data.emit(testEmptyFeedOn()); delay(delayCount); data.emit(testEmptyFeedOff()); delay(delayCount)
            // 네트워크 연결 실패 테스트
//            data.emit(testFailedConnectionOn()); delay(delayCount); data.emit(testFailedConnectionOff()); delay(delayCount)
            // 피드 테스트
            data.emit(getTestFeedList(context)); delay(delayCount);
//            data.emit(FeedFragmentUIstate()); delay(delayCount);
        }
    }
    return data
}

fun testRefreshingOn(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isRefreshing = true)
}

fun testRefreshingOff(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isRefreshing = false)
}

fun testProgressOn(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isProgess = true)
}

fun testProgressOff(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isProgess = false)
}

fun testEmptyFeedOn(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isEmptyFeed = true)
}

fun testEmptyFeedOff(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isEmptyFeed = false)
}

fun testFailedConnectionOn(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isFailedConnection = true)
}

fun testFailedConnectionOff(): FeedFragmentUIstate {
    return FeedFragmentUIstate(isFailedConnection = false)
}

fun getTestEmptyFeedFragmentUIstate(): FeedFragmentUIstate {
    return FeedFragmentUIstate(
        isRefreshing = false,
        isProgess = false,
        isLogin = false,
        reLoad = {},
        onAddReviewClickListener = { false },
        onRefreshListener = {}
    )
}

fun getTestFeedList(
    context: Context
): FeedFragmentUIstate {
    val list = getFeedsByFile(context = context)
    return FeedFragmentUIstate(feeds = list)
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