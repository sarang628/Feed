package com.posco.feedscreentestapp.di

import com.example.screen_feed.FeedService
import com.sarang.base_feed.data.Feed
import com.sryang.torang_repository.data.AppDatabase
import com.sryang.torang_repository.data.dao.FeedDao
import com.sryang.torang_repository.data.dao.PictureDao
import com.sryang.torang_repository.services.RemoteFeedServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.streams.toList

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Provides
    fun provideFeedService(
        remoteFeedServices: RemoteFeedServices
    ): FeedService {
        return object : FeedService {
            override suspend fun getFeeds(params: Map<String, String>): List<Feed> {
                return remoteFeedServices.getFeeds(params).stream().map {
                    Feed(
                        name = it.user!!.userName!!,
                        userId = it.user!!.userId,
                        profilePictureUrl = it.user!!.profilePicUrl!!,
                        contents = it.contents!!,
                        reviewImages = it.pictures.stream().map {
                            it.picture_url
                        }.toList(),
                        restaurantName = it.restaurant!!.restaurantName!!,
                        commentAmount = it.comment_amount!!,
                        isFavorite = it.favorite!!.isFavority!!,
                        isLike = it.like!!.isLike!!,
                        likeAmount = it.like_amount!!,
                        rating = it.rating!!,
                        reviewId = it.reviewId
                    )
                }.toList()
            }
        }
    }
}