package com.example.jsonplaceholder.comments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.BaseActivity
import com.example.jsonplaceholder.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_base_template.*


@Parcelize
data class Post(
        val postTitle: String,
        val postBody: String,
        val userId: String,
        val postId: String
) : Parcelable

class CommentsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val post = intent.getParcelableExtra<Post>(INTENT_POST)
                ?: throw IllegalStateException("field $INTENT_POST missing in Intent")
        val lifecycleOwner = this

        ViewModelProviders.of(this).get(CommentsViewModel::class.java).apply {
            user.observe(lifecycleOwner, Observer {
                loadAllComments(post.postId)
            })

            comments.observe(lifecycleOwner, Observer {
                val adapterData = AdapterData(post.postTitle, post.postBody, user.value!!, comments.value!!)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.VERTICAL }
                    if (adapter == null) {
                        adapter = CommentsAdapter(context, adapterData)
                    } else {
                        (adapter!! as CommentsAdapter).let {
                            it.adapterData = adapterData
                            it.notifyDataSetChanged()
                        }
                    }
                    showProgress(false)
                }
            })

            apiError.observe(lifecycleOwner, Observer {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.GONE
                errorView.text = it
            })

            if (shouldLoad()) {
                loadUser(post.userId)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    companion object {
        const val INTENT_POST = "intent_post"

        fun newIntent(context: Context, post: Post) = Intent(context, CommentsActivity::class.java).apply {
            putExtra(INTENT_POST, post)
        }
    }
}
