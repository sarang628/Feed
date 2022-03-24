package com.sryang.screenfeedtestapp1

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.torang_core.data.model.Feed
import com.example.torang_core.dialog.FeedDialogEventAdapter
import com.example.torang_core.dialog.FeedMyDialogEventAdapter
import com.example.torang_core.dialog.NotLoggedInFeedDialogEventAdapter
import com.example.torang_core.navigation.*
import com.example.torang_core.util.TorangShare
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject


class DummyMyMenuBottomSheetNavigation @Inject constructor() : MyMenuBottomSheetNavigation {
    override fun show(
        context: Context,
        myDialogEventAdapter: FeedMyDialogEventAdapter,
        feed: Feed
    ) {
    }

    override fun dismiss() {
    }
}

class DummyMenuBottomSheetNavigation @Inject constructor() : MenuBottomSheetNavigation {
    override fun show(context: Context, dialogEventAdapter: FeedDialogEventAdapter, feed: Feed) {
    }

    override fun dismiss() {
    }

}

class DummyShareBottomSheetNavigation @Inject constructor() : ShareBottomSheetNavigation {
    override fun show(context: Context) {
        Toast.makeText(context, "share", Toast.LENGTH_SHORT).show()
    }
}

class DummyTimeLineDetailNavigation @Inject constructor() : TimeLineDetailNavigation {
    override fun go(context: Context, userId: Int) {
        Toast.makeText(context, "TimeLineDetail", Toast.LENGTH_SHORT).show()
    }
}

class DummyProfileNavigation @Inject constructor() : ProfileNavigation {
    override fun go(context: Context, userId: Int) {
        Toast.makeText(context, "profile", Toast.LENGTH_SHORT).show()
    }
}

class DummyRestaurantDetailNavigation @Inject constructor() : RestaurantDetailNavigation {
    override fun go(context: Context, restaurantId: Int) {
    }

}

class DummyAddReviewNavigation @Inject constructor() : AddReviewNavigation {
    override fun go(context: Context, restaurantId: Int?, reviewId: Int) {

    }
}

class DummyLoginNavigation @Inject constructor() : LoginNavigation {
    override fun goLogin(fragmentManager: FragmentManager?) {
    }

    override fun goLogin(context: Context) {
    }
}

class DummyReportNavigation @Inject constructor() : ReportNavigation {
    override fun goReport(context: Context, reviewId: Int) {

    }

}

class DummyPicturePageNavigation @Inject constructor() : PicturePageNavigation {
    override fun go(context: Context, reviewId: Int, position: Int) {

    }
}

class DummyTorangShare @Inject constructor() : TorangShare {
    override fun shareLink(context: Context, url: String) {
        Toast.makeText(context, "shareLink", Toast.LENGTH_SHORT).show()
    }
}


class DummyNotLoggedInMenuBottomSheetNavigation @Inject constructor() : NotLoggedInMenuBottomSheetNavigation {
    override fun show(
        context: Context,
        myDialogEventAdapter: NotLoggedInFeedDialogEventAdapter,
        feed: Feed
    ) {
        Toast.makeText(context, "NotLoggedInMenu", Toast.LENGTH_SHORT).show()
    }

    override fun dismiss() {

    }

}



@Module
@InstallIn(ActivityComponent::class)
abstract class FeedsScreenNavigationModule {
    @Binds
    abstract fun provideAddReviewNavigation(dummyAddReviewNavigation: DummyAddReviewNavigation): AddReviewNavigation

    @Binds
    abstract fun provideLoginNavigation(dummyLoginNavigation: DummyLoginNavigation): LoginNavigation

    @Binds
    abstract fun provideRestaurantDetailNavigation(dummyRestaurantDetailNavigation: DummyRestaurantDetailNavigation): RestaurantDetailNavigation

    @Binds
    abstract fun provideProfileNavigation(dummyProfileNavigation: DummyProfileNavigation): ProfileNavigation

    @Binds
    abstract fun provideReportNavigation(dummyReportNavigation: DummyReportNavigation): ReportNavigation

    @Binds
    abstract fun providePicturePageNavigation(dummyPicturePageNavigation: DummyPicturePageNavigation): PicturePageNavigation

    @Binds
    abstract fun provideTimeLineDetailNavigation(dummyTimeLineDetailNavigation: DummyTimeLineDetailNavigation): TimeLineDetailNavigation

    @Binds
    abstract fun provideShareBottomSheetNavigation(dummyShareBottomSheetNavigation: DummyShareBottomSheetNavigation): ShareBottomSheetNavigation

    @Binds
    abstract fun provideTorangShare(dummyTorangShare: DummyTorangShare): TorangShare

    @Binds
    abstract fun provideMenuBottomSheetNavigation(dummyMenuBottomSheetNavigation: DummyMenuBottomSheetNavigation): MenuBottomSheetNavigation

    @Binds
    abstract fun provideMyMenuBottomSheetNavigation(dummyMyMenuBottomSheetNavigation: DummyMyMenuBottomSheetNavigation): MyMenuBottomSheetNavigation

    @Binds
    abstract fun provideNotLoggedInMenuBottomSheetNavigation(dummyNotLoggedInMenuBottomSheetNavigation: DummyNotLoggedInMenuBottomSheetNavigation): NotLoggedInMenuBottomSheetNavigation

}


