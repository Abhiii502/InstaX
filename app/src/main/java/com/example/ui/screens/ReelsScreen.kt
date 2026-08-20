package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.ReelItem
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ReelsScreen(
    reels: List<ReelItem>,
    onLikeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { reels.size })

    VerticalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
            .background(InstaBlack)
            .testTag("reels_screen")
    ) { page ->
        val reel = reels[page]
        ReelPageItem(
            reel = reel,
            onLikeClick = { onLikeClick(reel.id) }
        )
    }
}

@Composable
fun ReelPageItem(
    reel: ReelItem,
    onLikeClick: () -> Unit
) {
    var showBigHeart by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val likeScale by animateFloatAsState(
        targetValue = if (reel.isLiked) 1.25f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "reelLikeScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(reel.id) {
                detectTapGestures(
                    onDoubleTap = {
                        if (!reel.isLiked) {
                            onLikeClick()
                        }
                        scope.launch {
                            showBigHeart = true
                            delay(800)
                            showBigHeart = false
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Video poster / image background
        AsyncImage(
            model = reel.videoImageUrl,
            contentDescription = "Reel video",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Subtle gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        startY = 400f
                    )
                )
        )

        // Animated double-tap heart
        androidx.compose.animation.AnimatedVisibility(
            visible = showBigHeart,
            enter = scaleIn(initialScale = 0.3f) + fadeIn(),
            exit = scaleOut(targetScale = 1.3f) + fadeOut()
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = InstaRed.copy(alpha = 0.95f),
                modifier = Modifier.size(110.dp)
            )
        }

        // Bottom User Info & Audio Info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.78f)
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = reel.avatar,
                    contentDescription = reel.user,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = reel.user,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Text(
                text = reel.caption,
                color = Color.White,
                fontSize = 13.sp,
                lineHeight = 17.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Audio",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = reel.songTitle,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }
        }

        // Right Action Bar (Likes, Comments, Share, Audio Disc)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Like
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onLikeClick) {
                    Icon(
                        imageVector = if (reel.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like Reel",
                        tint = if (reel.isLiked) InstaRed else Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .scale(likeScale)
                    )
                }
                Text(
                    text = NumberFormat.getNumberInstance(Locale.US).format(reel.likes),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Comments
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Reel Comments",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = NumberFormat.getNumberInstance(Locale.US).format(reel.comments),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Share
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = "Share Reel",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            // More Options
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Reel Options",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Rotating audio equalizer / album art icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF27272A)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.GraphicEq,
                    contentDescription = "Audio Equalizer",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
