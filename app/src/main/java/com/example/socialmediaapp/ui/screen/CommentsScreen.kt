package com.example.socialmediaapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.socialmediaapp.data.model.Comments
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmediaapp.viewmodel.MainViewModel
import kotlin.random.Random

@Composable
fun CommentsScreenRoute(viewModel : MainViewModel = hiltViewModel(), postId : Int){
    val commentsByPostId = viewModel.getCommentsByPostId(postId).collectAsLazyPagingItems()
    CommentsScreen(comments = commentsByPostId)
}

@Composable
internal fun CommentsScreen(comments : LazyPagingItems<Comments>){
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 65.dp)
        ){
            items(comments.itemCount){index->
                comments[index]?.let { comment->
                    CommentsScreenItems(
                        comments = comment,
                        modifier = Modifier
                            .fillMaxWidth()
                        )
                }

            }
            comments.apply {
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                val loading = when {
                    loadState.prepend is LoadState.Loading -> loadState.prepend as LoadState.Loading
                    loadState.append is LoadState.Loading -> loadState.append as LoadState.Loading
                    loadState.refresh is LoadState.Loading -> loadState.refresh as LoadState.Loading
                    else -> null
                }

                if(loading != null){
                    repeat((0..20).count()){
                        item {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.DarkGray)
                            ){
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
                if(error != null){
                    error.error.localizedMessage?.let { Log.e("Error",it)}
                }

            }

        }

    }
}


@Composable
fun CommentsScreenItems(
    comments : Comments,
    modifier:  Modifier = Modifier
){
    val color = remember{ Color(Random.nextInt(),Random.nextInt(),Random.nextInt()) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(9.dp),
        elevation = 4.dp,
        backgroundColor = color
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ){
            Text(
                text = comments.name,
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Normal)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comments.body,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.3.dp)
                    .background(color = Color.LightGray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier  = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = comments.email,
                    color = Color.White,
                    fontStyle = FontStyle.Normal
                )
            }
        }


    }
}

@Preview
@Composable
fun CommentsScreenPreview(){
    SocialMediaAppTheme {
        CommentsScreenItems(
            comments = Comments(
                id = 1,
                body = "Deneme",
                email = "deneme@hotmail.com",
                name =  "Abidin",
                postId = 1
            )
        )
    }
}

