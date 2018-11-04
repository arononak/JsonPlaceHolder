package com.example.jsonplaceholder.posts

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.PostObject
import com.example.jsonplaceholder.R
import com.example.jsonplaceholder.comments.CommentsActivity
import com.example.jsonplaceholder.comments.Post
import kotlinx.android.synthetic.main.item_post.view.*


class PostsAdapter(private val context: Context, var items: ArrayList<PostObject>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder.create(context, parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].apply {
            (holder as PostViewHolder).let { it ->
                val post = Post(title, body, userId.toString(), id.toString())
                it.title.text = title
                it.description.text = body
                it.itemView.apply {
                    startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
                    setOnClickListener {
                        (context as Activity).apply {
                            startActivity(CommentsActivity.newIntent(context, post))
                            overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stay)
                        }
                    }
                }
            }
        }
    }

    companion object {
        class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title = view.text_title!!
            val description = view.text_description!!

            companion object {
                fun create(context: Context, parent: ViewGroup) = PostViewHolder(inflateLayout(context, R.layout.item_post, parent))
            }
        }

        private fun inflateLayout(context: Context, layoutId: Int, parent: ViewGroup) = LayoutInflater.from(context).inflate(layoutId, parent, false)
    }
}
