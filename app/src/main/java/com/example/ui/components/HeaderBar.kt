package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MinimalBg
import com.example.ui.theme.MinimalError
import com.example.ui.theme.MinimalPrimaryContainer
import com.example.ui.theme.MinimalTextPrimary

@Composable
fun HeaderBar(
    onNotificationsClick: () -> Unit,
    onDirectMessagesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MinimalBg)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App Title "instaX" Clean Minimalism header
        Text(
            text = "instaX",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (-0.5).sp,
            color = MinimalTextPrimary,
            modifier = Modifier.testTag("app_logo_title")
        )

        // Action Icons (Activity Heart & DMs Chat)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = MinimalPrimaryContainer),
                        onClick = onNotificationsClick
                    )
                    .testTag("notifications_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Notifications",
                    tint = MinimalTextPrimary,
                    modifier = Modifier.size(24.dp)
                )
                // Notification Dot
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(MinimalError, CircleShape)
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = MinimalPrimaryContainer),
                        onClick = onDirectMessagesClick
                    )
                    .testTag("direct_messages_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Direct Messages",
                    tint = MinimalTextPrimary,
                    modifier = Modifier.size(24.dp)
                )
                // DM unread badge
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .background(MinimalError, CircleShape)
                        .align(Alignment.TopEnd)
                        .offset(x = (-6).dp, y = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

