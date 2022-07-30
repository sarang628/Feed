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
            btnFeedLayoutTest.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, FeedLayoutTestActivity::class.java)
                )
            }

            btnItemFeedBottomTest.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, ItemFeedBottomTestActivity::class.java)
                )
            }

            btnItemFeedTopTest.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, ItemFeedTopTestActivity::class.java)
                )
            }

            btnItemTimeLineTest.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, ItemTimeLineTestActivity::class.java)
                )
            }

            btnFeedsFragmentTest.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, FeedsFragmentTestActivity::class.java)
                )
            }

            btnTimeLineListTest.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, ItemTimeLineListTestActivity::class.java)
                )
            }
        }

    }
}