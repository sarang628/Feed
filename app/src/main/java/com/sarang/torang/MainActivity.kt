package com.sarang.torang

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.di.feed_di.ProvideFeedScreen
import com.sarang.torang.di.feed_di.ProvideMyFeedScreen
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.repository.ProfileRepository
import com.sarang.torang.repository.ProfileRepositoryTest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var profileRepository: ProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar()?.hide();

        setContent {
            var reviewId by remember { mutableStateOf("0") }
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Row {
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "MyFeed")
                            }
                            OutlinedTextField(value = reviewId, onValueChange = {
                                try {
                                    reviewId = it
                                } catch (e: Exception) {

                                }
                            })
                        }
                        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp - 30).dp)) {
                            ProvideFeedScreen(
                            onAddReview = {},
                        )
                            /*ProvideMyFeedScreen(
                                reviewId = try { Integer.parseInt(reviewId) } catch (e: Exception) { 0 }
                            )*/
                        }
                        LoginRepositoryTest(loginRepository = loginRepository)
                        ProfileRepositoryTest(profileRepository = profileRepository)
                    }
                }
            }
        }

    }
}