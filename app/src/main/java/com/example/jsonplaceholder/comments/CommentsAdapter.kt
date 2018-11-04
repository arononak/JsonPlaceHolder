package com.example.jsonplaceholder.comments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.CommentObject
import com.example.jsonplaceholder.R
import com.example.jsonplaceholder.UserObject
import kotlinx.android.synthetic.main.item_comment_header.view.*
import kotlinx.android.synthetic.main.item_comment_item.view.*


data class AdapterData(
        val postTitle: String,
        val postBody: String,
        val user: UserObject,
        val comments: ArrayList<CommentObject>
)

class CommentsAdapter(private val context: Context, var adapterData: AdapterData) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = adapterData.comments.size + 1

    override fun getItemViewType(position: Int) = if (position == 0) TYPE_HEADER else TYPE_ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_HEADER -> HeaderViewHolder.create(context, parent)
        TYPE_ITEM   -> ItemViewHolder.create(context, parent)
        else        -> throw IllegalStateException("Adapter overflow")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
        when (holder) {
            is HeaderViewHolder -> holder.apply {
                title.text = adapterData.postTitle
                description.text = adapterData.postBody
                author.run {
                    text = adapterData.user.name
                    setOnClickListener {
                        openEmail(context, adapterData.user.email)
                    }
                }
            }
            is ItemViewHolder -> holder.apply {
                adapterData.comments[position - 1].run {
                    holder.name.text = name
                    holder.email.apply {
                        text = email
                        setOnClickListener {
                            openEmail(context, email)
                        }
                    }
                    holder.comment.text = body
                }
            }
        }
    }

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_ITEM = 2

        private class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title = view.text_title!!
            val description = view.text_description!!
            val author = view.text_author!!

            companion object {
                fun create(context: Context, parent: ViewGroup) = HeaderViewHolder(inflateLayout(context, R.layout.item_comment_header, parent))
            }
        }

        private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name = view.text_name!!
            val email = view.text_email!!
            val comment = view.text_comment!!

            companion object {
                fun create(context: Context, parent: ViewGroup) = ItemViewHolder(inflateLayout(context, R.layout.item_comment_item, parent))
            }
        }

        private fun openEmail(context: Context, email: String) {
            Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null)).apply {
                putExtra(Intent.EXTRA_EMAIL, email)
                resolveActivity(context.packageManager)?.also {
                    context.startActivity(this)
                }
            }
        }

        private fun inflateLayout(context: Context, layoutId: Int, parent: ViewGroup) = LayoutInflater.from(context).inflate(layoutId, parent, false)
    }
}
