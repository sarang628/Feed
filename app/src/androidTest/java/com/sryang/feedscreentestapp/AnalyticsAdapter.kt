package com.sryang.feedscreentestapp

import dagger.hilt.android.scopes.ActivityScoped
import org.junit.Assert.assertEquals
import org.junit.Test
import javax.inject.Inject

@ActivityScoped
class AnalyticsAdapter @Inject constructor(
    //private val service: AnalyticsService
) {

}

class AnalyticsAdapterTest {

    @Test
    fun Happy_path() {
        // You don't need Hilt to create an instance of AnalyticsAdapter.
        // You can pass a fake or mock AnalyticsService.
        //val adapter = AnalyticsAdapter(fakeAnalyticsService)
        //assertEquals(...)
    }
}