package com.posco.feedscreentestapp.di.repositories

import android.content.Context
import com.sryang.torang_repository.data.AppDatabase
import com.sryang.torang_repository.repository.*
import com.sryang.torang_repository.repository.impl.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {


/** 로컬 데이터베이스 제공 */

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepositoryModule {
    @Binds
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun provideNationRepository(nationRepositoryImpl: NationRepositoryImpl): NationRepository

    @Binds
    abstract fun provideMyReviewsRepository(myReviewsRepositoryImpl: MyReviewsRepositoryImpl): MyReviewsRepository

    @Binds
    abstract fun provideMyReviewRepository(myReviewRepositoryImpl: MyReviewRepositoryImpl): MyReviewRepository

    @Binds
    abstract fun provideFindRepository(findRepository: FindRepositoryImpl): FindRepository

    @Binds
    abstract fun provideFilterRepository(filterRepository: FilterRepositoryImpl): FilterRepository

}
