package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_feed.TestFeedsScreen
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.entity.FeedEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class FeedsFragmentTestActivity : ComponentActivity() {

    @Inject
    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestFeedsScreen()
            Button(onClick = {
                testViewModel.test()
            }) {
                Text(text = "Button")
            }
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