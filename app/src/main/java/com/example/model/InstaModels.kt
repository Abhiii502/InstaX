package com.example.model

data class Post(
    val id: Long,
    val user: String,
    val avatar: String,
    val image: String,
    val likes: Int,
    val caption: String,
    val location: String = "Sidhauli, India",
    val timeAgo: String = "2 hours ago",
    val commentsCount: Int = 14,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false
)

data class Story(
    val id: Long,
    val username: String,
    val avatar: String,
    val storyImage: String,
    val timestamp: String = "3h",
    val isSeen: Boolean = false,
    val isUser: Boolean = false
)

data class Comment(
    val id: Long,
    val user: String,
    val avatar: String,
    val text: String,
    val timeAgo: String = "1h",
    val likes: Int = 0,
    val isLiked: Boolean = false
)

data class ReelItem(
    val id: Long,
    val user: String,
    val avatar: String,
    val videoImageUrl: String,
    val caption: String,
    val songTitle: String,
    val likes: Int,
    val comments: Int,
    val isLiked: Boolean = false
)

data class ExploreItem(
    val id: Long,
    val imageUrl: String,
    val isReel: Boolean = false,
    val likesCount: Int = 340
)

data class DirectMessage(
    val id: Long,
    val user: String,
    val avatar: String,
    val lastMessage: String,
    val time: String,
    val unread: Boolean = false
)
