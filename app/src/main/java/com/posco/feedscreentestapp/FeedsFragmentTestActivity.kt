package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.FeedsViewModel
import com.example.screen_feed.TestFeedsScreen
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.entity.FeedEntity
import com.sryang.torang_repository.test.FeedRepositoryTest
import com.sryang.torang_repository.test.FeedTestMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FeedsFragmentTestActivity : ComponentActivity() {

    @Inject
    lateinit var feedsViewModel: FeedsViewModel

    @Inject
    lateinit var feedDao: FeedDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestFeedsScreen(feedsViewModel = feedsViewModel)
//
//            Feed 데이터 테스트
//            FeedRepositoryTest(context = LocalContext.current, feedDao = feedDao)
            
        }
    }
}

class TestViewModel @Inject constructor(
    val feedDao: FeedDao
) : ViewModel() {
    fun test() {
        viewModelScope.launch {
            feedDao.insertAll(
                ArrayList<FeedEntity>().apply {
                    add(
                        FeedEntity(
                            review_id = 0,
                            userId = 0
                        )
                    )
                }
            )
        }
    }

    init {
    }

}