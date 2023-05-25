package com.example.socialmediaapp.ui.screen


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.socialmediaapp.data.model.Albums
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmediaapp.viewmodel.MainViewModel
@Composable
fun AlbumsScreensRoute(
    viewModel : MainViewModel = hiltViewModel(),
    navigateToDetail : (String) -> Unit
    ){
    val albums = viewModel.getAlbums().collectAsLazyPagingItems()
    AlbumsScreen(albums = albums, navigateToDetail = {
        navigateToDetail.invoke(it)
    })
}
@Composable
internal fun AlbumsScreen(
    albums : LazyPagingItems<Albums>,
    navigateToDetail: (String) -> Unit
){
    Box(modifier = Modifier.fillMaxSize())
    {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 65.dp)

        ){
            items(albums.itemCount){index->
                albums[index]?.let{album->
                    AlbumsScreensItems(
                        albums = album,
                        modifier = Modifier.
                                clickable { navigateToDetail.invoke(album.id.toString()) }
                    )
                }

            }
            albums.apply {
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
fun AlbumsScreensItems(
    albums : Albums,
    modifier : Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = 5.dp
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp)
        ){
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .weight(3f),
                verticalArrangement = Arrangement.Center
            ){
               Text(
                   text = albums.title,
                   fontStyle = FontStyle.Normal,
                   fontSize = 20.sp,
                   fontWeight = FontWeight.Bold,
                   textAlign = TextAlign.Center,
                   fontFamily = FontFamily.SansSerif,
                   modifier = Modifier.fillMaxWidth(),
               )
                Spacer(modifier = Modifier.height(12.dp))
            }

        }
    }
}

@Preview
@Composable
fun AlbumsScreenPreview(){
    SocialMediaAppTheme {
        AlbumsScreensItems(
            albums = Albums(
                id = 1,
                title = "Deneme",
                userId = 1
            )
        )
    }
}
