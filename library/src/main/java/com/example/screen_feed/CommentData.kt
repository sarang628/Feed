package com.example.screen_feed

data class CommentData(
    val userId: Int,
    val profileImageUrl: String,
    val date: String,
    val comment: String,
    val name: String,
    val likeCount: Int
)
