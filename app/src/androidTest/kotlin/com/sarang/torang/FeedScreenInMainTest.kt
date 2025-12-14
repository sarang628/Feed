package com.sarang.torang

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.feed.FeedScreenInMain
import com.sarang.torang.core.database.dao.FeedDao
import com.sarang.torang.core.database.dao.MainFeedDao
import com.sarang.torang.core.database.dao.MyFeedDao
import com.sarang.torang.repository.FeedRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeedScreenInMainTest {

    @Inject lateinit var feedRepository: FeedRepository
    @Inject lateinit var feedDao: FeedDao
    @Inject lateinit var mainFeedDao: MainFeedDao

    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Before fun setUp() { hiltRule.inject() }
    @get:Rule(order = 1) val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun feedScreenInMainTest() {
        composeTestRule.setContent {
            FeedScreenInMain()
        }
    }

    @Test
    fun loadTest() = runTest{
        feedRepository.loadByPage(0)
        assertEquals(false, mainFeedDao.findAllFlow().first().isEmpty())
//        assertEquals(false, feedDao.findAllFlow().first().isEmpty())
    }
}