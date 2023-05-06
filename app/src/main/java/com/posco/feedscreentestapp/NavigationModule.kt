package com.posco.feedscreentestapp

import androidx.fragment.app.Fragment
import com.example.navigation.AddReviewNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {
    @Provides
    fun navigate(): AddReviewNavigation {
        return object : AddReviewNavigation {
            override fun navigate(fragment: Fragment) {

            }
        }
    }
}