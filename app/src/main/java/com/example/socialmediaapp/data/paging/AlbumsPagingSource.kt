package com.example.socialmediaapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmediaapp.data.model.Albums
import com.example.socialmediaapp.data.remote.api.ApiService

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
            val start = params.key ?: 0
            val response = api.getAlbums(start,1)
            if(response.isSuccessful){
                val albums  = response.body() ?: emptyList()

                LoadResult.Page(
                    data = albums,
                    prevKey = if(start == 0) null else start - 1,
                    nextKey = if(albums.isEmpty()) null else start + 1
                )
            } else {
                return LoadResult.Error(Exception("Failed"))
            }


        }catch (e : Exception){
            LoadResult.Error(e)
        }


    }

}