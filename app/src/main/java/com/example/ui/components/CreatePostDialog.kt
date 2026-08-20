package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaBlue
import com.example.ui.theme.InstaBorder
import com.example.ui.theme.InstaCardBg
import com.example.ui.theme.InstaStoryGradient
import com.example.ui.theme.InstaTextMuted
import com.example.ui.theme.InstaTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onPostCreated: (imageUrl: String, caption: String, location: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val sampleImagePresets = listOf(
        "https://picsum.photos/600/600?random=101",
        "https://picsum.photos/600/600?random=102",
        "https://picsum.photos/600/600?random=103",
        "https://picsum.photos/600/600?random=104",
        "https://picsum.photos/600/600?random=105",
        "https://picsum.photos/600/600?random=106"
    )

    var selectedImageUrl by remember { mutableStateOf(sampleImagePresets[0]) }
    var customImageUrl by remember { mutableStateOf("") }
    var caption by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Sidhauli, India") }

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
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .testTag("create_post_bottom_sheet")
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = Color.White
                    )
                }

                Text(
                    text = "New Post",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Button(
                    onClick = {
                        val finalImage = customImageUrl.ifBlank { selectedImageUrl }
                        onPostCreated(finalImage, caption, location)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InstaBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("submit_post_button")
                ) {
                    Text(text = "Share", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Preview Image
            val activeImage = customImageUrl.ifBlank { selectedImageUrl }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(InstaBlack)
                    .border(1.dp, InstaBorder, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = activeImage,
                    contentDescription = "Post preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Preset Photo Chooser
            Text(
                text = "Choose Photo Preset:",
                fontSize = 13.sp,
                color = InstaTextSecondary,
                fontWeight = FontWeight.SemiBold
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(sampleImagePresets) { presetUrl ->
                    val isSelected = selectedImageUrl == presetUrl && customImageUrl.isBlank()
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = if (isSelected) 2.5.dp else 1.dp,
                                color = if (isSelected) InstaBlue else InstaBorder,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                selectedImageUrl = presetUrl
                                customImageUrl = ""
                            }
                    ) {
                        AsyncImage(
                            model = presetUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(InstaBlue.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Caption Field
            OutlinedTextField(
                value = caption,
                onValueChange = { caption = it },
                label = { Text("Write a caption...", color = InstaTextMuted) },
                placeholder = { Text("Share what's on your mind... #instaX", color = InstaTextMuted) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("caption_input_field"),
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InstaBlack,
                    unfocusedContainerColor = InstaBlack,
                    focusedBorderColor = InstaBorder,
                    unfocusedBorderColor = InstaBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Location Field
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Add Location", color = InstaTextMuted) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = InstaBlue
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InstaBlack,
                    unfocusedContainerColor = InstaBlack,
                    focusedBorderColor = InstaBorder,
                    unfocusedBorderColor = InstaBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
