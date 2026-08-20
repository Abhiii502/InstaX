package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.InstaBlue
import com.example.ui.theme.InstaBorder
import com.example.ui.theme.InstaCardBg
import com.example.ui.theme.InstaTextMuted

data class NotificationItem(
    val id: Long,
    val avatar: String,
    val username: String,
    val actionText: String,
    val timeAgo: String,
    val postPreview: String? = null,
    val isFollowAction: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsSheet(
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val notifications = listOf(
        NotificationItem(1, "https://i.pravatar.cc/150?img=5", "priya_99", "liked your photo in Sidhauli.", "15m", "https://picsum.photos/600/600?random=1"),
        NotificationItem(2, "https://i.pravatar.cc/150?img=12", "rahul_editz", "started following you.", "1h", isFollowAction = true),
        NotificationItem(3, "https://i.pravatar.cc/150?img=9", "ananya_creative", "commented: 'Love this composition! ✨'", "3h", "https://picsum.photos/600/700?random=3"),
        NotificationItem(4, "https://i.pravatar.cc/150?img=15", "kabir_vlogs", "liked your story.", "5h")
    )

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
                .testTag("notifications_sheet")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
                Text(
                    text = "Activity",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color.White
                )
                Box(modifier = Modifier.size(48.dp))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notifications.size) { index ->
                    val notif = notifications[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = notif.avatar,
                            contentDescription = notif.username,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                        )

                        val annotated = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                                append(notif.username)
                                append(" ")
                            }
                            withStyle(SpanStyle(color = Color(0xFFE4E4E7))) {
                                append(notif.actionText)
                                append(" ")
                            }
                            withStyle(SpanStyle(color = InstaTextMuted, fontSize = 11.sp)) {
                                append(notif.timeAgo)
                            }
                        }

                        Text(
                            text = annotated,
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f),
                            lineHeight = 17.sp
                        )

                        if (notif.isFollowAction) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = InstaBlue),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Follow", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        } else if (notif.postPreview != null) {
                            AsyncImage(
                                model = notif.postPreview,
                                contentDescription = "Post preview",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(6.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}
