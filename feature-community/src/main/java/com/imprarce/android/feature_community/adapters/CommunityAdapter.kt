package com.imprarce.android.feature_community.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.feature_community.R
import com.imprarce.android.local.community.CommunityItem

class CommunityAdapter(private val communityList: List<CommunityItem>) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = communityList[position]
//        holder.titleTextView.text = currentItem.title
//        holder.descriptionTextView.text = currentItem.description
//        holder.ratingNumberTextView.text = currentItem.rating.toString()
//        holder.imageView.setImageResource(currentItem.imageResource)
//        holder.arrowRatingImageView.setImageResource(currentItem.arrowRatingImageResource)
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val ratingNumberTextView: TextView = itemView.findViewById(R.id.rating_number)
        val imageView: ImageView = itemView.findViewById(R.id.icon_user)
        val arrowRatingImageView: ImageView = itemView.findViewById(R.id.arrow_rating)
    }
}