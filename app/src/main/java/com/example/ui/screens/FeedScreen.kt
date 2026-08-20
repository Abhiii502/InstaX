package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.Post
import com.example.model.Story
import com.example.ui.components.PostCard
import com.example.ui.components.StoriesRow
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaBorder

@Composable
fun FeedScreen(
    stories: List<Story>,
    posts: List<Post>,
    onStoryClick: (Story) -> Unit,
    onAddStoryClick: () -> Unit,
    onLikeClick: (Long) -> Unit,
    onCommentClick: (Post) -> Unit,
    onShareClick: (Post) -> Unit,
    onSaveClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(InstaBlack)
            .testTag("feed_screen")
    ) {
        // Stories Section
        item {
            StoriesRow(
                stories = stories,
                onStoryClick = onStoryClick,
                onAddStoryClick = onAddStoryClick
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(InstaBorder)
            )
        }

        // Posts List
        items(posts, key = { it.id }) { post ->
            PostCard(
                post = post,
                onLikeClick = { onLikeClick(post.id) },
                onCommentClick = { onCommentClick(post) },
                onShareClick = { onShareClick(post) },
                onSaveClick = { onSaveClick(post.id) }
            )
        }
    }
}
