package com.example.socialmediaapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewComfyAlt
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route : String,
    val title : String,
    val icon : ImageVector?
) {
    object Post : Screens(
        route = "posts",
        title = "Posts",
        icon = Icons.Filled.ViewTimeline
    )

    object Album : Screens(
        route = "albums",
        title = "Albums",
        icon = Icons.Filled.ViewComfyAlt
    )

    object Comments : Screens(
        route = "comments",
        title = "comments",
        icon =   null
    )

    object Photos : Screens(
        route = "photos",
        title = "photos",
        icon = null
    )
}