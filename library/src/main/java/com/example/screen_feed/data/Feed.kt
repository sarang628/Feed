package com.example.screen_feed.data

data class Feed(
    val reviewId: Int? = null,
    val name:String? = null,
    val restaurantName : String? = null,
    val rating : Float? = null,
    val profilePictureUrl : String? = null,
    val likeAmount : Int? = null,
    val commentAmount : Int? = null,
    val author : String? = null,
    val isLike : Boolean? = null,
    val isFavorite : Boolean? = null,
    val comment : String? = null,
    val reviewImages : List<String>? = null
)
