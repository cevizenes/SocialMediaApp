package com.example.socialmediaapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.socialmediaapp.data.model.Photos
import com.example.socialmediaapp.viewmodel.MainViewModel

@Composable
fun PhotosScreenRoute(
    viewModel : MainViewModel = hiltViewModel(),
    albumId : String,
){
    val photosByAlbumId = viewModel.getPhotosByAlbumId(albumId.toInt()).collectAsLazyPagingItems()
    PhotosScreen(photos = photosByAlbumId)
    }




@Composable
internal fun PhotosScreen(
    photos : LazyPagingItems<Photos>
){

    Box(
        modifier = Modifier.fillMaxSize()
        ){
        LazyVerticalGrid(columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(bottom = 65.dp),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),


            content ={
            items(photos.itemCount){index->
                photos[index]?.let { photo->
                    PhotosScreenItems(
                        photos = photo,
                        modifier = Modifier
                    )
                }

            }
            photos.apply {
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
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }
                }
                if(error != null){
                    error.error.localizedMessage?.let { Log.e("Error",it)}
                }

            }

        } )
    }
}



@Composable
fun PhotosScreenItems(
    photos : Photos,
    modifier : Modifier = Modifier
){
    val signal = remember { (mutableStateOf(false)) }
        Box(modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        signal.value = !signal.value
                    }
                )
            }
            ,
            contentAlignment = Alignment.Center,

        ){
            if(signal.value){
                Dialog(onDismissRequest = {signal.value = false}) {
                    Box(modifier.fillMaxSize()){
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photos.url)
                                .crossfade(true)
                                .size(600,600)
                                .build(),
                            contentDescription = "Urls",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(600.dp).align(Alignment.Center),

                            )
                    }
                    
                }

            }
            else{
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photos.thumbnailUrl)
                        .crossfade(true)
                        .size(150,150)
                        .build(),
                    contentDescription = "Thumbnail Urls",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(150.dp),

                    )
            }




        }

}



