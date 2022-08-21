package com.posco.feedscreentestapp.di

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.torang_core.navigation.LoginNavigation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import javax.inject.Singleton

/*@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun provideLoginMudule(torangLoginManager: TestTorangLoginManager): LoginManager
}*/

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    @Singleton // Provide always the same instance
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        // Run this code when providing an instance of CoroutineScope
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}

/*@Module
@InstallIn(SingletonComponent::class)
abstract class FacebookLoginProviderModule {
@Binds
abstract fun provideFacebookLoginProvider(facebookLoginProviderImpl: FacebookLoginProviderImpl): FacebookLoginProvider
}*/

@Module
@InstallIn(ActivityComponent::class)
abstract class LoginNavigationModule {
    @Binds
    abstract fun provideLoginNavigation(loginNavigationImpl: LoginNavigationImpl): LoginNavigation
}

class LoginNavigationImpl @Inject constructor() : LoginNavigation {
    override fun goLogin(fragmentManager: FragmentManager?) {
        TODO("Not yet implemented")
    }

    override fun goLogin(context: Context) {
        //context.startActivity(Intent(context, LoginActivity::class.java))
        Toast.makeText(context, "goLogin", Toast.LENGTH_SHORT).show()
    }
}