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
    suspend fun getPosts() : Response<List<Posts>>

    @GET("comments?{postId}")
    suspend fun getComments(
        @Query("postId") postId : Int
    ) : Response<List<Comments>>

    @GET("albums")
    suspend fun getAlbums() : Response<List<Albums>>

    @GET("photos?{albumId}")
    suspend fun getPhotos(
        @Query("albumId") albumId : Int
    ) : Response<List<Photos>>

}