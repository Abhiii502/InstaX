package com.example.data

import com.example.model.Comment
import com.example.model.DirectMessage
import com.example.model.ExploreItem
import com.example.model.Post
import com.example.model.ReelItem
import com.example.model.Story

object SampleData {
    val initialPosts = listOf(
        Post(
            id = 1L,
            user = "priya_99",
            avatar = "https://i.pravatar.cc/150?img=5",
            image = "https://picsum.photos/600/600?random=1",
            likes = 1240,
            caption = "Sunset vibes in Sidhauli 🌅 #instaX #vibes",
            location = "Sidhauli, Uttar Pradesh",
            timeAgo = "2h ago",
            commentsCount = 28
        ),
        Post(
            id = 2L,
            user = "rahul_editz",
            avatar = "https://i.pravatar.cc/150?img=12",
            image = "https://picsum.photos/600/800?random=2",
            likes = 890,
            caption = "My first post on instaX 🔥 Let's connect!",
            location = "Lucknow, India",
            timeAgo = "4h ago",
            commentsCount = 15
        ),
        Post(
            id = 3L,
            user = "ananya_creative",
            avatar = "https://i.pravatar.cc/150?img=9",
            image = "https://picsum.photos/600/700?random=3",
            likes = 3450,
            caption = "Coffee and late night designs ☕✨ Aesthetic mode on.",
            location = "Bengaluru, India",
            timeAgo = "6h ago",
            commentsCount = 42
        ),
        Post(
            id = 4L,
            user = "travel_diaries_in",
            avatar = "https://i.pravatar.cc/150?img=33",
            image = "https://picsum.photos/600/600?random=4",
            likes = 5120,
            caption = "Lost in the Himalayan mist 🏔️ Trekking memories!",
            location = "Manali, Himachal Pradesh",
            timeAgo = "1d ago",
            commentsCount = 89
        )
    )

    val stories = (0..7).map { i ->
        Story(
            id = (i + 1).toLong(),
            username = if (i == 0) "Your Story" else "user_$i",
            avatar = "https://i.pravatar.cc/150?img=${i + 10}",
            storyImage = "https://picsum.photos/600/1000?random=${i + 15}",
            timestamp = "${i + 1}h",
            isSeen = i > 4,
            isUser = i == 0
        )
    }

    val comments = listOf(
        Comment(1, "rahul_editz", "https://i.pravatar.cc/150?img=12", "Stunning shot! 🔥✨", "1h ago", 12),
        Comment(2, "ananya_creative", "https://i.pravatar.cc/150?img=9", "Sidhauli sunset hits different ❤️", "45m ago", 8),
        Comment(3, "kabir_vlogs", "https://i.pravatar.cc/150?img=15", "Colors are incredible bro!", "30m ago", 3),
        Comment(4, "sneha_art", "https://i.pravatar.cc/150?img=22", "Love the composition! 📸", "15m ago", 5)
    )

    val reels = listOf(
        ReelItem(
            id = 1,
            user = "priya_99",
            avatar = "https://i.pravatar.cc/150?img=5",
            videoImageUrl = "https://picsum.photos/600/1100?random=11",
            caption = "Golden hour magic in Sidhauli ✨ #instaX #reelsindia",
            songTitle = "Original Audio - priya_99 • Sunset Dreams",
            likes = 45200,
            comments = 890
        ),
        ReelItem(
            id = 2,
            user = "dev_adventures",
            avatar = "https://i.pravatar.cc/150?img=33",
            videoImageUrl = "https://picsum.photos/600/1100?random=22",
            caption = "3 tips for building modern Android apps with Jetpack Compose 🚀",
            songTitle = "Lo-Fi Beats • Code & Chill",
            likes = 18900,
            comments = 412
        ),
        ReelItem(
            id = 3,
            user = "rahul_editz",
            avatar = "https://i.pravatar.cc/150?img=12",
            videoImageUrl = "https://picsum.photos/600/1100?random=33",
            caption = "Cinematic drone shots over the river valley 🌊🚁",
            songTitle = "Trending Beat • Soundwave Audio",
            likes = 94300,
            comments = 1530
        )
    )

    val exploreItems = (1..18).map { id ->
        ExploreItem(
            id = id.toLong(),
            imageUrl = "https://picsum.photos/400/400?random=${id + 50}",
            isReel = id % 3 == 0,
            likesCount = 200 + id * 75
        )
    }

    val directMessages = listOf(
        DirectMessage(1, "priya_99", "https://i.pravatar.cc/150?img=5", "Hey! Loved your recent post on instaX", "10m ago", true),
        DirectMessage(2, "rahul_editz", "https://i.pravatar.cc/150?img=12", "Sent a photo", "2h ago", true),
        DirectMessage(3, "ananya_creative", "https://i.pravatar.cc/150?img=9", "Let me know when the UI is ready!", "Yesterday", false),
        DirectMessage(4, "kabir_vlogs", "https://i.pravatar.cc/150?img=15", "Nice vibes bro 🚀", "2d ago", false)
    )
}
