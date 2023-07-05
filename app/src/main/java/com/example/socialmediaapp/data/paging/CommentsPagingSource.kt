package com.example.socialmediaapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmediaapp.data.model.Comments
import com.example.socialmediaapp.data.remote.api.ApiService

class CommentsPagingSource(
    private val api : ApiService,
    private val postId : Int
) : PagingSource<Int, Comments>() {
    override fun getRefreshKey(state: PagingState<Int, Comments>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comments> {
       return try {
           val start = params.key ?: 0
           val response = api.getComments(postId,start,params.loadSize)
           if(response.isSuccessful){
               val comments = response.body() ?: emptyList()

               LoadResult.Page(
                   data = comments,
                   prevKey = if(start == 0) null else start - params.loadSize,
                   nextKey = if(comments.isEmpty()) null else start + params.loadSize
               )
           }else{
               return LoadResult.Error(Exception("Failed"))
           }

       }catch (e: Exception){
           LoadResult.Error(e)
       }
    }
}