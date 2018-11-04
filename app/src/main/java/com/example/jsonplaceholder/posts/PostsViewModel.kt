package com.example.jsonplaceholder.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jsonplaceholder.JsonPlaceHolderService
import com.example.jsonplaceholder.PostObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PostsViewModel : ViewModel() {
    val posts = MutableLiveData<ArrayList<PostObject>>()
    val apiError = MutableLiveData<String>()

    fun loadAllPosts() {
        JsonPlaceHolderService.apiService.getPosts().enqueue(object : Callback<ArrayList<PostObject>> {
            override fun onResponse(call: Call<ArrayList<PostObject>>, response: Response<ArrayList<PostObject>>) {
                if (response.isSuccessful) {
                    posts.value = response.body()
                } else {
                    apiError.value = "Connection to server failed"
                }
            }

            override fun onFailure(call: Call<ArrayList<PostObject>>, t: Throwable) {
                apiError.value = t.message
            }
        })
    }

    fun shouldLoad() = posts.value == null || posts.value!!.size == 0
}