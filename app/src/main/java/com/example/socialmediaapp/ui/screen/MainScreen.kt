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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaapp.extensions.AddItems



@Composable
fun MainScreen(){
   val navController = rememberNavController()
   Scaffold(
        bottomBar = {
          BottomBar(navController)
        }
   ) { padding->
       Box(modifier = Modifier.padding(
           PaddingValues(bottom = padding.calculateBottomPadding())
       ))
     BottomNavGraph(navController)
   }
}


@Composable
fun BottomBar(navController: NavHostController){
    val appScreens = listOf(
        BottomBarScreen.Post,
        BottomBarScreen.Album
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        appScreens.forEach{appScreen->
          AddItems(
              appScreen = appScreen,
              currentDestination = currentDestination,
              navController = navController )
        }
    }
}


@Composable
fun BottomNavGraph(navController: NavHostController){
   NavHost(
       navController = navController,
       startDestination = BottomBarScreen.Post.route
   ){

       composable(route = BottomBarScreen.Post.route){
           PostsScreenRoute()
       }
       composable(route = BottomBarScreen.Album.route){
           AlbumsScreensRoute()
       }
   }
}
