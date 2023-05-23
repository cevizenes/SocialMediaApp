package com.example.socialmediaapp.extensions

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.socialmediaapp.ui.screen.BottomBarScreen

@Composable
fun RowScope.AddItems(
    appScreen : BottomBarScreen,
    currentDestination : NavDestination?,
    navController : NavHostController
){
    BottomNavigationItem(
        label = {
            Text(text = appScreen.title)
        },
        icon = {
            Icon(
                imageVector = appScreen.icon,
                contentDescription = "Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any{ it.route == appScreen.route } == true,
        unselectedContentColor = LocalContentColor.current.copy(ContentAlpha.disabled),
        onClick = {
            navController.navigate(appScreen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}