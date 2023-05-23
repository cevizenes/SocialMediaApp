package com.example.socialmediaapp.network.api

import com.example.socialmediaapp.data.model.Albums
import com.example.socialmediaapp.data.model.Comments
import com.example.socialmediaapp.data.model.Photos
import com.example.socialmediaapp.data.model.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {

       const val baseUrl = "https://jsonplaceholder.typicode.com/"
    }

    @GET("posts")
    suspend fun getPosts(
        @Query("_start") start : Int,
        @Query("_limit") limit : Int
    ) : Response<List<Posts>>

    @GET("posts/{postId}/comments")
    suspend fun getComments(
        @Path("postId") postId : Int,
        @Query("_start") start : Int,
        @Query("_limit") limit : Int

    ) : Response<List<Comments>>

    @GET("albums")
    suspend fun getAlbums(
        @Query("_start") start : Int,
        @Query("_limit") limit : Int
    ) : Response<List<Albums>>

    @GET("albums/{albumId}/photos")
    suspend fun getPhotos(
        @Path("albumId") albumId : Int,
        @Query("_start") start :Int,
        @Query("_limit") limit : Int
    ) : Response<List<Photos>>

}