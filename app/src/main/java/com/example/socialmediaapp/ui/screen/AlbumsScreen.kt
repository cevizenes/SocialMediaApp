package com.example.socialmediaapp.ui.screen


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AlbumsScreen(){
    Text(
        text = "Albums",
        fontSize = MaterialTheme.typography.h3.fontSize,
        fontWeight =  FontWeight.Bold,
        color = Color.Black

    )
}