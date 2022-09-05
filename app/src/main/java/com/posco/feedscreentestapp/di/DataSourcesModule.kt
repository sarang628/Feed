package com.posco.feedscreentestapp.di

import com.sryang.torang_core.data.entity.ReviewImage
import com.sryang.torang_repository.datasource.FeedRemoteDataSource
import com.sryang.torang_repository.datasource.MyReviewsLocalDataSource
import com.sryang.torang_repository.datasource.MyReviewsRemoteDataSource
import com.sryang.torang_repository.datasource.impl.FeedRemoteDataSourceImpl
import com.sryang.torang_repository.data.entity.ReviewImageEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {
    @Binds
    abstract fun provideLocalDataSource(
        dataSource: MyReviewsLocalDataSourceImpl
    ): MyReviewsLocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(
        dataSource: MyReviewsRemoteDataSourceImpl
    ): MyReviewsRemoteDataSource

    @Binds
    abstract fun provideFeedRemoteDataSource(
        dataSource: FeedRemoteDataSourceImpl
    ): FeedRemoteDataSource
}

class MyReviewsLocalDataSourceImpl @Inject constructor() : MyReviewsLocalDataSource {
    override suspend fun getMyReviews(): List<ReviewImage> {
        return ArrayList()
    }
}

class MyReviewsRemoteDataSourceImpl @Inject constructor() : MyReviewsRemoteDataSource {
    override suspend fun getMyReviews(): List<ReviewImageEntity> {
        return ArrayList()
    }
}