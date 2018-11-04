package com.example.jsonplaceholder.posts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.BaseActivity
import kotlinx.android.synthetic.main.activity_base_template.*


class PostsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProviders.of(this).get(PostsViewModel::class.java).let { it ->
            it.posts.observe(this, Observer {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.VERTICAL }
                    if (adapter == null) {
                        adapter = PostsAdapter(context, it)
                    } else {
                        (adapter!! as PostsAdapter).apply {
                            items = it
                            notifyDataSetChanged()
                        }
                    }
                    showProgress(false)
                }
            })

            it.apiError.observe(this, Observer {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.GONE
                errorView.text = it
            })

            if (it.shouldLoad()) {
                it.loadAllPosts()
            }
        }
    }
}
