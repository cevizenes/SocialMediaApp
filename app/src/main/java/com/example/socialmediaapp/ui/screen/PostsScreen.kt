package com.example.socialmediaapp.ui.screen


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.socialmediaapp.data.model.Posts

import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmediaapp.viewmodel.MainViewModel


@Composable
fun PostsScreen(mainViewModel: MainViewModel = hiltViewModel()
){
    val posts = mainViewModel.getPosts().collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = posts.loadState){
        if(posts.loadState.refresh is LoadState.Error){
            Toast.makeText(context,"Error: " + (posts.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        if(posts.loadState.refresh is LoadState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }else{
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(posts){post->
                    if(post!= null){
                        PostsScreenItems(
                            post = post,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
                item{
                    if(posts.loadState.append is LoadState.Loading){
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

}



    
@Composable
fun PostsScreenItems(
    post : Posts,
    modifier : Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = 5.dp
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp)

        ){
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.body,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Preview
@Composable
fun PostsItemPreview(){
    SocialMediaAppTheme {
        PostsScreenItems(
            post = Posts(
               body = "Deneme",
               id = 1,
               title = "Deneme",
               userId = 1
            ),
            modifier = Modifier.fillMaxWidth()
            )

    }
}
