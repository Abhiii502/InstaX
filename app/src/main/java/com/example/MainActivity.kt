package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ui.components.CommentsBottomSheet
import com.example.ui.components.CreatePostDialog
import com.example.ui.components.DirectMessagesSheet
import com.example.ui.components.HeaderBar
import com.example.ui.components.NotificationsSheet
import com.example.ui.components.StoryViewerDialog
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.FeedScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ReelsScreen
import com.example.ui.theme.InstaBlack
import com.example.ui.theme.InstaBorder
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.InstaXViewModel
import com.example.viewmodel.NavTab
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                InstaXApp()
            }
        }
    }
}

@Composable
fun InstaXApp(
    viewModel: InstaXViewModel = viewModel()
) {
    val posts by viewModel.posts.collectAsState()
    val stories by viewModel.stories.collectAsState()
    val reels by viewModel.reels.collectAsState()
    val exploreItems by viewModel.exploreItems.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedStory by viewModel.selectedStory.collectAsState()
    val activeCommentPost by viewModel.activeCommentPost.collectAsState()
    val commentsMap by viewModel.commentsMap.collectAsState()
    val isCreatePostOpen by viewModel.isCreatePostOpen.collectAsState()
    val isDMsOpen by viewModel.isDMsOpen.collectAsState()
    val isNotificationsOpen by viewModel.isNotificationsOpen.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = InstaBlack,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (currentTab == NavTab.HOME) {
                HeaderBar(
                    onNotificationsClick = { viewModel.openNotifications(true) },
                    onDirectMessagesClick = { viewModel.openDMs(true) },
                    modifier = Modifier.statusBarsPadding()
                )
            }
        },
        bottomBar = {
            InstaBottomNav(
                currentTab = currentTab,
                onTabSelect = { tab -> viewModel.selectTab(tab) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                NavTab.HOME -> {
                    FeedScreen(
                        stories = stories,
                        posts = posts,
                        onStoryClick = { story -> viewModel.openStory(story) },
                        onAddStoryClick = { viewModel.openCreatePost() },
                        onLikeClick = { postId -> viewModel.toggleLike(postId) },
                        onCommentClick = { post -> viewModel.openComments(post) },
                        onShareClick = { post ->
                            scope.launch {
                                snackbarHostState.showSnackbar("Post link copied to clipboard!")
                            }
                        },
                        onSaveClick = { postId ->
                            viewModel.toggleSave(postId)
                            scope.launch {
                                snackbarHostState.showSnackbar("Saved to your collection")
                            }
                        }
                    )
                }

                NavTab.EXPLORE -> {
                    ExploreScreen(
                        exploreItems = exploreItems,
                        modifier = Modifier.statusBarsPadding()
                    )
                }

                NavTab.ADD -> {
                    // Handled via create dialog trigger
                }

                NavTab.REELS -> {
                    ReelsScreen(
                        reels = reels,
                        onLikeClick = { reelId -> viewModel.toggleReelLike(reelId) }
                    )
                }

                NavTab.PROFILE -> {
                    ProfileScreen(
                        userPosts = posts,
                        onOpenCreate = { viewModel.openCreatePost() },
                        modifier = Modifier.statusBarsPadding()
                    )
                }
            }
        }
    }

    // Story Viewer Modal
    selectedStory?.let { story ->
        StoryViewerDialog(
            story = story,
            onDismiss = { viewModel.closeStory() },
            onNextStory = { viewModel.nextStory() },
            onPreviousStory = { viewModel.previousStory() }
        )
    }

    // Comments Bottom Sheet
    activeCommentPost?.let { post ->
        val comments = commentsMap[post.id] ?: emptyList()
        CommentsBottomSheet(
            post = post,
            comments = comments,
            onDismiss = { viewModel.closeComments() },
            onAddComment = { text -> viewModel.addComment(post.id, text) }
        )
    }

    // Create Post Dialog
    if (isCreatePostOpen) {
        CreatePostDialog(
            onDismiss = { viewModel.closeCreatePost() },
            onPostCreated = { imageUrl, caption, location ->
                viewModel.createPost(imageUrl, caption, location)
                scope.launch {
                    snackbarHostState.showSnackbar("Post shared to feed! 🎉")
                }
            }
        )
    }

    // Direct Messages Sheet
    if (isDMsOpen) {
        DirectMessagesSheet(
            onDismiss = { viewModel.openDMs(false) }
        )
    }

    // Activity Notifications Sheet
    if (isNotificationsOpen) {
        NotificationsSheet(
            onDismiss = { viewModel.openNotifications(false) }
        )
    }
}

@Composable
fun InstaBottomNav(
    currentTab: NavTab,
    onTabSelect: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(InstaBlack)
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(InstaBorder)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 8.dp)
                .testTag("bottom_nav_bar"),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Tab
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onTabSelect(NavTab.HOME) }
                    .testTag("nav_home"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (currentTab == NavTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home Feed",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Search Tab
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onTabSelect(NavTab.EXPLORE) }
                    .testTag("nav_search"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (currentTab == NavTab.EXPLORE) Icons.Filled.Search else Icons.Outlined.Search,
                    contentDescription = "Explore",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Add Post Tab
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onTabSelect(NavTab.ADD) }
                    .testTag("nav_add"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddBox,
                    contentDescription = "Add Post",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Reels Tab
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onTabSelect(NavTab.REELS) }
                    .testTag("nav_reels"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (currentTab == NavTab.REELS) Icons.Filled.Movie else Icons.Outlined.Movie,
                    contentDescription = "Reels",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Profile Tab
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onTabSelect(NavTab.PROFILE) }
                    .testTag("nav_profile"),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (currentTab == NavTab.PROFILE) 2.dp else 0.dp,
                            color = if (currentTab == NavTab.PROFILE) Color.White else Color.Transparent,
                            shape = CircleShape
                        )
                        .padding(if (currentTab == NavTab.PROFILE) 2.dp else 0.dp)
                ) {
                    AsyncImage(
                        model = "https://i.pravatar.cc/150?img=68",
                        contentDescription = "Your Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}
