package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Post
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaBorder
import com.example.ui.theme.InstaRed
import com.example.ui.theme.InstaStoryGradient
import com.example.ui.theme.InstaTextMuted
import com.example.ui.theme.InstaTextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PostCard(
    post: Post,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    var showBigHeart by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val likeScale by animateFloatAsState(
        targetValue = if (post.isLiked) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "likeScale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(InstaBlack)
            .padding(bottom = 12.dp)
            .testTag("post_card_${post.id}")
    ) {
        // --- Post Header ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // User Avatar with delicate gradient outline
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(InstaStoryGradient)
                        .padding(1.5.dp)
                        .background(InstaBlack, CircleShape)
                        .padding(1.dp)
                ) {
                    AsyncImage(
                        model = post.avatar,
                        contentDescription = post.user,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                    )
                }

                Column {
                    Text(
                        text = post.user,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    if (post.location.isNotBlank()) {
                        Text(
                            text = post.location,
                            fontSize = 11.sp,
                            color = InstaTextSecondary
                        )
                    }
                }
            }

            // More menu (3 dots)
            Box {
                IconButton(
                    onClick = { isMenuOpen = true },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = isMenuOpen,
                    onDismissRequest = { isMenuOpen = false },
                    modifier = Modifier.background(InstaBlack)
                ) {
                    DropdownMenuItem(
                        text = { Text("Share Link", color = Color.White) },
                        onClick = {
                            isMenuOpen = false
                            onShareClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Save Post", color = Color.White) },
                        onClick = {
                            isMenuOpen = false
                            onSaveClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("About this Account", color = Color.White) },
                        onClick = { isMenuOpen = false }
                    )
                }
            }
        }

        // --- Post Media Image with Double-Tap to Like ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color(0xFF18181B))
                .pointerInput(post.id) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (!post.isLiked) {
                                onLikeClick()
                            }
                            scope.launch {
                                showBigHeart = true
                                delay(900)
                                showBigHeart = false
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = post.image,
                contentDescription = "Post image by ${post.user}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            // Animated Big Heart on Double Tap
            androidx.compose.animation.AnimatedVisibility(
                visible = showBigHeart,
                enter = scaleIn(
                    initialScale = 0.3f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(),
                exit = scaleOut(
                    targetScale = 1.3f,
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut()
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = InstaRed.copy(alpha = 0.95f),
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        // --- Action Buttons Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like Button
                IconButton(
                    onClick = onLikeClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("like_button_${post.id}")
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (post.isLiked) "Unlike" else "Like",
                        tint = if (post.isLiked) InstaRed else Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .scale(if (post.isLiked) likeScale else 1f)
                    )
                }

                // Comment Button
                IconButton(
                    onClick = onCommentClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("comment_button_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comments",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Share / Direct Button
                IconButton(
                    onClick = onShareClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("share_button_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Send,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Bookmark / Save Button
            IconButton(
                onClick = onSaveClick,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("save_button_${post.id}")
            ) {
                Icon(
                    imageVector = if (post.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = if (post.isSaved) "Unsave" else "Save",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // --- Likes Count & Caption & Comments Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val formattedLikes = NumberFormat.getNumberInstance(Locale.US).format(post.likes)
            Text(
                text = "$formattedLikes likes",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // User Caption
            val annotatedCaption = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                    append(post.user)
                    append(" ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = Color(0xFFF4F4F5))) {
                    append(post.caption)
                }
            }

            Text(
                text = annotatedCaption,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )

            // View all comments affordance
            if (post.commentsCount > 0) {
                Text(
                    text = "View all ${post.commentsCount} comments",
                    fontSize = 13.sp,
                    color = InstaTextMuted,
                    modifier = Modifier
                        .clickable(onClick = onCommentClick)
                        .padding(vertical = 2.dp)
                )
            }

            // Timestamp
            Text(
                text = post.timeAgo,
                fontSize = 11.sp,
                color = InstaTextMuted
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(InstaBorder)
        )
    }
}
