package com.example.socialmediaapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewComfyAlt
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val icon : ImageVector
) {
    object Post : BottomBarScreen(
        route = "posts",
        title = "Posts",
        icon = Icons.Filled.ViewTimeline
    )

    object Album : BottomBarScreen(
        route = "albums",
        title = "Albums",
        icon = Icons.Filled.ViewComfyAlt
    )

}