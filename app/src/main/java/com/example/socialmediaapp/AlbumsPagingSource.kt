package com.example.socialmediaapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmediaapp.data.model.Albums
import com.example.socialmediaapp.network.api.ApiService

class AlbumsPagingSource(
    private val api : ApiService
) : PagingSource<Int, Albums>() {
    override fun getRefreshKey(state: PagingState<Int, Albums>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Albums> {
        return try {
            val page = params.key ?: 1
            val response = api.getAlbums()
            if(response.isSuccessful){
                val albums  = response.body() ?: emptyList()
                val nextKey = if(albums.isEmpty()) null else page + 1
                LoadResult.Page(
                    data = albums,
                    prevKey = if(page == 1) null else page - 1,
                    nextKey = nextKey
                )
            } else {
                return LoadResult.Error(Exception("Failed"))
            }


        }catch (e : Exception){
            LoadResult.Error(e)
        }


    }

}