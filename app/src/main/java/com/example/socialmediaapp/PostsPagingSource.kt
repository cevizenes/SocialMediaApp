package com.example.socialmediaapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmediaapp.data.model.Posts
import com.example.socialmediaapp.network.api.ApiService
import java.lang.Exception

class PostsPagingSource(
    private val api : ApiService
) : PagingSource<Int, Posts>()
{
    override fun getRefreshKey(state: PagingState<Int, Posts>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Posts> {
        return try {
            val page = params.key ?: 1
            val response = api.getPosts()
            if(response.isSuccessful){
                val posts = response.body() ?: emptyList()
                val nextKey = if(posts.isEmpty()) null else page + 1
                LoadResult.Page(
                    data = posts,
                    prevKey = if(page == 1) null else page - 1,
                    nextKey = nextKey
                )
            }else{
                return LoadResult.Error(Exception("Failed"))
            }


        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}