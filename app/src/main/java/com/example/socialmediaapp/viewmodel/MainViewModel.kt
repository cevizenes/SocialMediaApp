package com.example.socialmediaapp.viewmodel

import androidx.compose.animation.splineBasedDecay
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.socialmediaapp.AlbumsPagingSource
import com.example.socialmediaapp.CommentsPagingSource
import com.example.socialmediaapp.PhotosPagingSource
import com.example.socialmediaapp.PostsPagingSource
import com.example.socialmediaapp.data.model.Albums
import com.example.socialmediaapp.data.model.Comments
import com.example.socialmediaapp.data.model.Photos
import com.example.socialmediaapp.data.model.Posts
import com.example.socialmediaapp.network.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api : ApiService
) : ViewModel()
{

    fun getAlbums() : Flow<PagingData<Albums>>{
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = {
                AlbumsPagingSource(api)
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getPosts() : Flow<PagingData<Posts>>{
        return Pager(
            config= PagingConfig(pageSize = 1),
            pagingSourceFactory = {
                PostsPagingSource(api)
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getPhotosByAlbumId(id : Int) :Flow<PagingData<Photos>>{
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = {
                PhotosPagingSource(api,id)
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getCommentsByPostId(id : Int) : Flow<PagingData<Comments>>{
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = {
                CommentsPagingSource(api,id)
            }
        ).flow.cachedIn(viewModelScope)
    }






   /* val albums : Flow<PagingData<Albums>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = {AlbumsPagingSource(api)}
    )   .flow
        .cachedIn(viewModelScope)

    val posts : Flow<PagingData<Posts>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = {PostsPagingSource(api)}
    )   .flow
        .cachedIn(viewModelScope)

    val photos :Flow<PagingData<Photos>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = {PhotosPagingSource(api,1)}
    )   .flow
        .cachedIn(viewModelScope)*/

}