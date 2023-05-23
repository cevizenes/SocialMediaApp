package com.example.socialmediaapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmediaapp.data.model.Photos
import com.example.socialmediaapp.network.api.ApiService
import kotlin.Exception

class PhotosPagingSource(
    private val api : ApiService,
    private val albumId : Int
) : PagingSource<Int, Photos>() {

    override fun getRefreshKey(state: PagingState<Int, Photos>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        return try {
            val start = params.key ?: 0
            val response = api.getPhotos(albumId,start,params.loadSize)
            if(response.isSuccessful){
                val photos = response.body() ?: emptyList()
                val nextKey = if(photos.isEmpty()) null else start + 1
                LoadResult.Page(
                    data = photos,
                    prevKey = if(start == 1) null else start - 1,
                    nextKey = nextKey
                )
            }else{
                return LoadResult.Error(Exception("Failed"))
            }

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}