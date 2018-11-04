package com.example.jsonplaceholder

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class PostObject(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
)

data class CommentObject(
        val postId: Int,
        val id: Int,
        val name: String,
        val email: String,
        val body: String
)

data class UserObject(
        val id: Int,
        val name: String,
        val username: String,
        val email: String,
        val phone: String,
        val website: String
)

interface JsonPlaceHolderService {

    @GET("posts")
    fun getPosts(): Call<ArrayList<PostObject>>

    @GET("comments")
    fun getComments(@Query("postId") postId: String): Call<ArrayList<CommentObject>>

    @GET("users/{id}")
    fun getUser(@Path("id") userId: String): Call<UserObject>

    companion object {
        val apiService: JsonPlaceHolderService by lazy {
            Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com")
                    .client(OkHttpClient.Builder()
                            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(JsonPlaceHolderService::class.java)
        }
    }
}
