package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.Comment
import com.example.model.ExploreItem
import com.example.model.Post
import com.example.model.ReelItem
import com.example.model.Story
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class NavTab(val title: String) {
    HOME("Home"),
    EXPLORE("Search"),
    ADD("Add"),
    REELS("Reels"),
    PROFILE("Profile")
}

class InstaXViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(SampleData.initialPosts)
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _stories = MutableStateFlow<List<Story>>(SampleData.stories)
    val stories: StateFlow<List<Story>> = _stories.asStateFlow()

    private val _reels = MutableStateFlow<List<ReelItem>>(SampleData.reels)
    val reels: StateFlow<List<ReelItem>> = _reels.asStateFlow()

    private val _exploreItems = MutableStateFlow<List<ExploreItem>>(SampleData.exploreItems)
    val exploreItems: StateFlow<List<ExploreItem>> = _exploreItems.asStateFlow()

    private val _selectedStory = MutableStateFlow<Story?>(null)
    val selectedStory: StateFlow<Story?> = _selectedStory.asStateFlow()

    private val _activeCommentPost = MutableStateFlow<Post?>(null)
    val activeCommentPost: StateFlow<Post?> = _activeCommentPost.asStateFlow()

    private val _commentsMap = MutableStateFlow<Map<Long, List<Comment>>>(
        mapOf(
            1L to SampleData.comments,
            2L to SampleData.comments.take(2)
        )
    )
    val commentsMap: StateFlow<Map<Long, List<Comment>>> = _commentsMap.asStateFlow()

    private val _isCreatePostOpen = MutableStateFlow(false)
    val isCreatePostOpen: StateFlow<Boolean> = _isCreatePostOpen.asStateFlow()

    private val _isDMsOpen = MutableStateFlow(false)
    val isDMsOpen: StateFlow<Boolean> = _isDMsOpen.asStateFlow()

    private val _isNotificationsOpen = MutableStateFlow(false)
    val isNotificationsOpen: StateFlow<Boolean> = _isNotificationsOpen.asStateFlow()

    private val _currentTab = MutableStateFlow(NavTab.HOME)
    val currentTab: StateFlow<NavTab> = _currentTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun selectTab(tab: NavTab) {
        if (tab == NavTab.ADD) {
            _isCreatePostOpen.value = true
        } else {
            _currentTab.value = tab
        }
    }

    fun toggleLike(postId: Long) {
        _posts.update { currentPosts ->
            currentPosts.map { post ->
                if (post.id == postId) {
                    val wasLiked = post.isLiked
                    post.copy(
                        isLiked = !wasLiked,
                        likes = if (wasLiked) post.likes - 1 else post.likes + 1
                    )
                } else {
                    post
                }
            }
        }
    }

    fun toggleSave(postId: Long) {
        _posts.update { currentPosts ->
            currentPosts.map { post ->
                if (post.id == postId) {
                    post.copy(isSaved = !post.isSaved)
                } else {
                    post
                }
            }
        }
    }

    fun openStory(story: Story) {
        _selectedStory.value = story
        _stories.update { list ->
            list.map { s -> if (s.id == story.id) s.copy(isSeen = true) else s }
        }
    }

    fun nextStory() {
        val current = _selectedStory.value ?: return
        val list = _stories.value
        val currentIndex = list.indexOfFirst { it.id == current.id }
        if (currentIndex != -1 && currentIndex < list.size - 1) {
            openStory(list[currentIndex + 1])
        } else {
            _selectedStory.value = null
        }
    }

    fun previousStory() {
        val current = _selectedStory.value ?: return
        val list = _stories.value
        val currentIndex = list.indexOfFirst { it.id == current.id }
        if (currentIndex > 0) {
            openStory(list[currentIndex - 1])
        }
    }

    fun closeStory() {
        _selectedStory.value = null
    }

    fun openComments(post: Post) {
        _activeCommentPost.value = post
    }

    fun closeComments() {
        _activeCommentPost.value = null
    }

    fun addComment(postId: Long, text: String) {
        if (text.isBlank()) return
        val newComment = Comment(
            id = System.currentTimeMillis(),
            user = "you_official",
            avatar = "https://i.pravatar.cc/150?img=68",
            text = text.trim(),
            timeAgo = "Just now",
            likes = 0
        )
        _commentsMap.update { currentMap ->
            val existing = currentMap[postId] ?: emptyList()
            currentMap + (postId to (listOf(newComment) + existing))
        }
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) post.copy(commentsCount = post.commentsCount + 1) else post
            }
        }
    }

    fun openCreatePost() {
        _isCreatePostOpen.value = true
    }

    fun closeCreatePost() {
        _isCreatePostOpen.value = false
    }

    fun createPost(imageUrl: String, caption: String, location: String) {
        val newPost = Post(
            id = System.currentTimeMillis(),
            user = "you_official",
            avatar = "https://i.pravatar.cc/150?img=68",
            image = imageUrl.ifBlank { "https://picsum.photos/600/600?random=${System.currentTimeMillis() % 100}" },
            likes = 1,
            isLiked = true,
            caption = caption.ifBlank { "New moments captured with instaX ✨" },
            location = location.ifBlank { "New Delhi, India" },
            timeAgo = "Just now",
            commentsCount = 0
        )
        _posts.update { listOf(newPost) + it }
        _isCreatePostOpen.value = false
        _currentTab.value = NavTab.HOME
    }

    fun toggleReelLike(reelId: Long) {
        _reels.update { currentReels ->
            currentReels.map { reel ->
                if (reel.id == reelId) {
                    val wasLiked = reel.isLiked
                    reel.copy(
                        isLiked = !wasLiked,
                        likes = if (wasLiked) reel.likes - 1 else reel.likes + 1
                    )
                } else reel
            }
        }
    }

    fun openDMs(open: Boolean) {
        _isDMsOpen.value = open
    }

    fun openNotifications(open: Boolean) {
        _isNotificationsOpen.value = open
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
