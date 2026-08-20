package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.model.ExploreItem
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaBorder
import com.example.ui.theme.InstaCardBg
import com.example.ui.theme.InstaTextMuted
import com.example.ui.theme.InstaTextSecondary

@Composable
fun ExploreScreen(
    exploreItems: List<ExploreItem>,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val categories = listOf("Trending", "Travel", "Photography", "Tech", "Architecture", "Style", "Food")
    var selectedCategory by remember { mutableStateOf("Trending") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(InstaBlack)
            .testTag("explore_screen")
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search tags, people, places...", color = InstaTextMuted, fontSize = 14.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = InstaTextMuted,
                    modifier = Modifier.size(22.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .testTag("explore_search_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = InstaCardBg,
                unfocusedContainerColor = InstaCardBg,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )

        // Filter Chips Row
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) Color.White else InstaCardBg,
                    modifier = Modifier.clickable { selectedCategory = category }
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.Black else Color.White,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // 3-Column Image Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(exploreItems, key = { it.id }) { item ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(InstaCardBg)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = "Explore media",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    if (item.isReel) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Reel",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(6.dp)
                                .size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
