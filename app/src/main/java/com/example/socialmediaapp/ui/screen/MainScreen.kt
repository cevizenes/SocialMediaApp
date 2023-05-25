package com.example.socialmediaapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.socialmediaapp.extensions.AddItems


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(
                PaddingValues(bottom = padding.calculateBottomPadding())
            )
        )
        NavGraph(navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val appScreens = listOf(
        Screens.Post,
        Screens.Album
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        appScreens.forEach { appScreen ->
            AddItems(
                appScreen = appScreen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable(route = "splash_screen"){
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Post.route) {
            PostsScreenRoute(
                navigateToDetail = {
                    navController.navigate("comments/$it")
                }
            )
        }
        composable(route = Screens.Album.route) {
            AlbumsScreensRoute(
                navigateToDetail = {
                    navController.navigate("photos/$it")
                }
            )
        }
        composable(
            route = "comments/{postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.StringType
                    defaultValue = "0"
                }
            )
        ) { navBackStackEntry ->
            CommentsScreenRoute(postId = navBackStackEntry.arguments?.getString("postId").orEmpty())

        }
        composable(
            route = "photos/{albumId}",
            arguments = listOf(
                navArgument("albumId"){
                    type = NavType.StringType
                    defaultValue = "0"
                }
            )
        ){
            navBackStackEntry ->
            PhotosScreenRoute(albumId = navBackStackEntry.arguments?.getString("albumId").orEmpty())

        }



    }
}
