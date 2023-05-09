package com.posco.feedscreentestapp

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.example.navigation.AddReviewNavigation
import com.example.navigation.FeedNavigations
import com.google.android.material.snackbar.Snackbar
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
                Snackbar.make(
                    fragment.requireView(),
                    "click AddReviewNavigation",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    @Provides
    fun feedNavigation(): FeedNavigations {
        return object : FeedNavigations {
            override fun goAddReview(context: Context, view: View) {
                Snackbar.make(view, "goAddReview", Snackbar.LENGTH_SHORT).show()
            }

            override fun goComment(context: Context, view: View) {
                Snackbar.make(view, "goComment", Snackbar.LENGTH_SHORT).show()
            }

            override fun goFullImage(context: Context, view: View) {
                Snackbar.make(view, "goFullImage", Snackbar.LENGTH_SHORT).show()
            }

            override fun goProfile(context: Context, view: View) {
                Snackbar.make(view, "goProfile", Snackbar.LENGTH_SHORT).show()
            }

            override fun goRestaurant(context: Context, view: View) {
                Snackbar.make(view, "goRestaurant", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}