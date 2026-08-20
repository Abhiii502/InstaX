package com.example.ui.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Post
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaBorder
import com.example.ui.theme.InstaCardBg
import com.example.ui.theme.InstaStoryGradient
import com.example.ui.theme.InstaTextMuted
import com.example.ui.theme.InstaTextSecondary

data class HighlightItem(val id: Long, val title: String, val cover: String)

@Composable
fun ProfileScreen(
    userPosts: List<Post>,
    onOpenCreate: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val highlights = listOf(
        HighlightItem(1, "Sidhauli 🌅", "https://picsum.photos/200/200?random=81"),
        HighlightItem(2, "Travel ✈️", "https://picsum.photos/200/200?random=82"),
        HighlightItem(3, "Design 🎨", "https://picsum.photos/200/200?random=83"),
        HighlightItem(4, "Tech 💻", "https://picsum.photos/200/200?random=84")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(InstaBlack)
            .testTag("profile_screen")
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "you_official",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onOpenCreate) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Create", tint = Color.White)
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }
            }
        }

        // Profile Bio & Statistics
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(InstaStoryGradient)
                    .padding(2.5.dp)
                    .background(InstaBlack, CircleShape)
                    .padding(2.dp)
            ) {
                AsyncImage(
                    model = "https://i.pravatar.cc/150?img=68",
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            // Stats
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatColumn(count = "${userPosts.size + 12}", label = "Posts")
                StatColumn(count = "2.4K", label = "Followers")
                StatColumn(count = "480", label = "Following")
            }
        }

        // Bio Text
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(text = "Abhishek Singh", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
            Text(text = "Creator & Android Developer 📱✨\nExploring Sidhauli & beyond 🚀\ninstaX enthusiast 🔥", color = Color(0xFFF4F4F5), fontSize = 13.sp, lineHeight = 17.sp)
            Text(text = "github.com/aistudio", color = Color(0xFF38BDF8), fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }

        // Action Buttons: Edit Profile & Share Profile
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27272A)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Edit profile", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27272A)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Share profile", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        // Story Highlights Row
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(highlights) { hl ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.width(62.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color(0xFF3F3F46), CircleShape)
                            .padding(3.dp)
                    ) {
                        AsyncImage(
                            model = hl.cover,
                            contentDescription = hl.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                    Text(
                        text = hl.title,
                        fontSize = 11.sp,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
        }

        // Tabs: Grid / Saved / Tagged
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = InstaBlack,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    height = 1.5.dp,
                    color = Color.White
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                icon = { Icon(imageVector = Icons.Default.GridOn, contentDescription = "Grid") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                icon = { Icon(imageVector = Icons.Default.BookmarkBorder, contentDescription = "Saved") }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                icon = { Icon(imageVector = Icons.Default.PersonOutline, contentDescription = "Tagged") }
            )
        }

        // Grid of Posts
        val gridImages = userPosts.map { it.image } + listOf(
            "https://picsum.photos/400/400?random=111",
            "https://picsum.photos/400/400?random=112",
            "https://picsum.photos/400/400?random=113",
            "https://picsum.photos/400/400?random=114",
            "https://picsum.photos/400/400?random=115",
            "https://picsum.photos/400/400?random=116"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(1.5.dp),
            verticalArrangement = Arrangement.spacedBy(1.5.dp)
        ) {
            items(gridImages) { imgUrl ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(InstaCardBg)
                ) {
                    AsyncImage(
                        model = imgUrl,
                        contentDescription = "User post",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun StatColumn(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = label, fontSize = 12.sp, color = InstaTextSecondary)
    }
}
