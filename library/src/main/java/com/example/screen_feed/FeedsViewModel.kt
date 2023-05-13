package com.example.screen_feed

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.example.screen_feed.uistate.getTestEmptyFeedFragmentUIstate
import com.example.screen_feed.uistate.getTestFeedList
import com.example.screen_feed.uistate.testEmptyFeedOff
import com.example.screen_feed.uistate.testEmptyFeedOn
import com.example.screen_feed.uistate.testFailedConnectionOff
import com.example.screen_feed.uistate.testFailedConnectionOn
import com.example.screen_feed.uistate.testProgressOff
import com.example.screen_feed.uistate.testProgressOn
import com.example.screen_feed.uistate.testRefreshingOff
import com.example.screen_feed.uistate.testRefreshingOn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedsViewModel(val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FeedsScreenUiState()
    )
    val uiState: StateFlow<FeedsScreenUiState> = _uiState

    init {
        testShowList()
    }

    fun test() {
        val delayCount = 1000L
        viewModelScope.launch {
            while (true) {
//             스와이프 리프레시 테스트
                _uiState.emit(testRefreshingOn()); delay(delayCount);
                _uiState.emit(testRefreshingOff()); delay(delayCount)
//             프로그레스 테스트
                _uiState.emit(testProgressOn()); delay(delayCount);
                _uiState.emit(testProgressOff()); delay(delayCount)
//             비어있는 피드 테스트
                _uiState.emit(testEmptyFeedOn()); delay(delayCount);
                _uiState.emit(testEmptyFeedOff()); delay(delayCount)
//             네트워크 연결 실패 테스트
                _uiState.emit(testFailedConnectionOn()); delay(delayCount);
                _uiState.emit(testFailedConnectionOff()); delay(delayCount)
//             피드 테스트
                _uiState.emit(getTestFeedList(context)); delay(delayCount);
            }
        }
    }

    fun testShowList() {
        viewModelScope.launch {
            _uiState.emit(getTestFeedList(context));
        }
    }

    fun testShowEmpty() {
        viewModelScope.launch {
            _uiState.emit(testEmptyFeedOn());
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = true,
                )
            )
            delay(2000)
            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = false
                )
            )
        }
    }

    fun clickName() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickName"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickRestaurant() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickRestaurant"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }

    }

    fun clickMenu() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickMenu"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }

    }

    fun clickImage() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickImage"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }

    }

    fun clickProfile() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickProfile"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickAddReview() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickAddReview"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickShare() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickShare"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickComment() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickComment"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickLike() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickLike"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

    fun clickFavorite() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(snackBar = "clickFavorite"))
            delay(1000)
            _uiState.emit(_uiState.value.copy(snackBar = null))
        }
    }

}