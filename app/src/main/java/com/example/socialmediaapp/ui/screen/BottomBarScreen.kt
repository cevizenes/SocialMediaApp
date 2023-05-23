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
        route = "post",
        title = "Post",
        icon = Icons.Filled.ViewTimeline
    )

    object Album : BottomBarScreen(
        route = "album",
        title = "Album",
        icon = Icons.Filled.ViewComfyAlt
    )

}