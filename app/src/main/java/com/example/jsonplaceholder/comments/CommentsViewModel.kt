package com.example.jsonplaceholder.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jsonplaceholder.CommentObject
import com.example.jsonplaceholder.JsonPlaceHolderService
import com.example.jsonplaceholder.UserObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CommentsViewModel : ViewModel() {

    val user = MutableLiveData<UserObject>()
    val comments = MutableLiveData<ArrayList<CommentObject>>()
    val apiError = MutableLiveData<String>()

    fun loadAllComments(postId: String) {
        JsonPlaceHolderService.apiService.getComments(postId).enqueue(object : Callback<ArrayList<CommentObject>> {
            override fun onResponse(call: Call<ArrayList<CommentObject>>, response: Response<ArrayList<CommentObject>>) {
                if (response.isSuccessful) {
                    comments.value = response.body()
                } else {
                    apiError.value = "Connection to server failed"
                }
            }

            override fun onFailure(call: Call<ArrayList<CommentObject>>, t: Throwable) {
                apiError.value = t.message
            }
        })
    }

    fun loadUser(userId: String) {
        JsonPlaceHolderService.apiService.getUser(userId).enqueue(object : Callback<UserObject> {
            override fun onResponse(call: Call<UserObject>, response: Response<UserObject>) {
                if (response.isSuccessful) {
                    user.value = response.body()
                } else {
                    apiError.value = "Connection to server failed"
                }
            }

            override fun onFailure(call: Call<UserObject>, t: Throwable) {
                apiError.value = t.message
            }
        })
    }

    fun shouldLoad() = user.value == null
}