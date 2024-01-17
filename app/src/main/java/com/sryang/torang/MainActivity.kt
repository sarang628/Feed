package com.sryang.torang

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.commonwidgets.torangcomposepack.AndroidViewRatingBar
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.widgets.RatingBar
import com.sryang.torang.di.feed_di.ProvideFeedScreen
import com.sryang.torang_repository.repository.LoginRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar()?.hide();

        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        ProvideFeedScreen(
                            ratingBar = { modifier, rating ->
                                AndroidViewRatingBar(
                                    modifier = modifier,
                                    rating = rating,
                                    isSmall = true
                                )
                            },
                            onAddReview = {},
                        )
                        //LoginRepositoryTest(loginRepository = loginRepository)
                    }
                }
            }
        }

    }
}