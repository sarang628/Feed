package com.posco.feedscreentestapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.posco.feedscreentestapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnFeedLayoutTest.setOnClickListener { goFeedLayoutTest() }
            btnItemTimeLineTest.setOnClickListener { goItemTimeLineTest() }
            btnFeedsFragmentTest.setOnClickListener { goFeedsFragmentTest() }
            btnTimeLineListTest.setOnClickListener { goItemTimeLineListTest() }
            btnBottomNavigationTest.setOnClickListener { goFeedNavigationTest() }
        }

    }

    private fun goFeedLayoutTest() {
        startActivity(
            Intent(this@MainActivity, FeedLayoutTestActivity::class.java)
        )
    }

    private fun goItemTimeLineTest() {
        startActivity(
            Intent(this@MainActivity, ItemTimeLineTestActivity::class.java)
        )
    }

    private fun goFeedsFragmentTest() {
        startActivity(
            Intent(this@MainActivity, FeedsFragmentTestActivity::class.java)
        )
    }

    private fun goItemTimeLineListTest() {
        startActivity(
            Intent(this@MainActivity, ItemTimeLineListTestActivity::class.java)
        )
    }

    private fun goFeedNavigationTest() {
        startActivity(
            Intent(this@MainActivity, FeedNavigationActivity::class.java)
        )
    }
}