package com.posco.feedscreentestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.posco.feedscreentestapp.databinding.ActivityFeedNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFeedNavigationBinding>(this, R.layout.activity_feed_navigation)
        setupWithNavController(R.id.fcv_feed_nav_test, binding.bottomNavigationView)
    }
}

fun AppCompatActivity.setupWithNavController(id : Int, bottomNavigation : BottomNavigationView){
    bottomNavigation.setupWithNavController(
        (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
    )
}