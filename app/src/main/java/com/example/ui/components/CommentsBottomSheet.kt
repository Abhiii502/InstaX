package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Comment
import com.example.model.Post
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaBlue
import com.example.ui.theme.InstaBorder
import com.example.ui.theme.InstaCardBg
import com.example.ui.theme.InstaRed
import com.example.ui.theme.InstaTextMuted
import com.example.ui.theme.InstaTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    post: Post,
    comments: List<Comment>,
    onDismiss: () -> Unit,
    onAddComment: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var textInput by remember { mutableStateOf("") }
    val quickEmojis = listOf("❤️", "🙌", "🔥", "😍", "👏", "✨", "💯")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = InstaCardBg,
        contentColor = Color.White,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 8.dp)
                    .size(width = 36.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(InstaTextMuted)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .navigationBarsPadding()
                .imePadding()
                .testTag("comments_bottom_sheet")
        ) {
            // Header
            Text(
                text = "Comments",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(InstaBorder)
            )

            // Comments List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Post Owner Caption as first item
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = post.avatar,
                            contentDescription = post.user,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = post.user,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = post.timeAgo,
                                    fontSize = 11.sp,
                                    color = InstaTextMuted
                                )
                            }
                            Text(
                                text = post.caption,
                                fontSize = 13.sp,
                                color = Color(0xFFF4F4F5),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                items(comments, key = { it.id }) { comment ->
                    CommentRow(comment = comment)
                }
            }

            // Quick Emoji Reaction Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(InstaBlack)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                quickEmojis.forEach { emoji ->
                    Text(
                        text = emoji,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable {
                                textInput += emoji
                            }
                            .padding(4.dp)
                    )
                }
            }

            // Comment Input Field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(InstaBlack)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = "https://i.pravatar.cc/150?img=68",
                    contentDescription = "Your avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )

                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    placeholder = { Text("Add a comment for ${post.user}...", color = InstaTextMuted, fontSize = 13.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("comment_text_input"),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF27272A),
                        unfocusedContainerColor = Color(0xFF27272A),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                IconButton(
                    onClick = {
                        if (textInput.isNotBlank()) {
                            onAddComment(textInput)
                            textInput = ""
                        }
                    },
                    enabled = textInput.isNotBlank(),
                    modifier = Modifier.testTag("send_comment_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Post Comment",
                        tint = if (textInput.isNotBlank()) InstaBlue else InstaTextMuted
                    )
                }
            }
        }
    }
}

@Composable
fun CommentRow(comment: Comment) {
    var isLiked by remember { mutableStateOf(comment.isLiked) }
    var likesCount by remember { mutableStateOf(comment.likes) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = comment.avatar,
            contentDescription = comment.user,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = comment.user,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = comment.timeAgo,
                    fontSize = 11.sp,
                    color = InstaTextMuted
                )
            }
            Text(
                text = comment.text,
                fontSize = 13.sp,
                color = Color(0xFFF4F4F5),
                modifier = Modifier.padding(top = 2.dp)
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Reply",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = InstaTextMuted
                )
                if (likesCount > 0) {
                    Text(
                        text = "$likesCount likes",
                        fontSize = 11.sp,
                        color = InstaTextMuted
                    )
                }
            }
        }

        IconButton(
            onClick = {
                isLiked = !isLiked
                likesCount = if (isLiked) likesCount + 1 else likesCount - 1
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Like Comment",
                tint = if (isLiked) InstaRed else InstaTextMuted,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
