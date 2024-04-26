package com.imprarce.android.feature_community.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imprarce.android.feature_community.R
import com.imprarce.android.local.comment.CommentItem
import com.mikhaellopez.circularimageview.CircularImageView

class CommentAdapter(private val commentList: List<CommentItem>, private val context: Context) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view_comments, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = commentList[position]

        Glide.with(context)
            .load(currentItem.userImage)
            .placeholder(R.drawable.image_plug)
            .into(holder.iconUser)

        holder.userName.text = currentItem.userName

        holder.message.text = currentItem.message
    }

    override fun getItemCount() = commentList.size

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconUser: CircularImageView = itemView.findViewById(R.id.icon_user)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val message: TextView = itemView.findViewById(R.id.message)

    }
}