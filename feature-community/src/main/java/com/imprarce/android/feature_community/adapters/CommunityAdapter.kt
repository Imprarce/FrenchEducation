package com.imprarce.android.feature_community.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imprarce.android.feature_community.R
import com.imprarce.android.feature_community.helpers.OnCommunityItemClickListener
import com.imprarce.android.feature_community.helpers.OnDeleteItemClickListener
import com.imprarce.android.local.community.CommunityItem

class CommunityAdapter(
    private val context: Context,
    private val communityList: List<CommunityItem>,
    private val userId: String,
    private val onDeleteItemClickListener: OnDeleteItemClickListener<CommunityItem>,
    private val itemClickListener: OnCommunityItemClickListener
) :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = communityList[position]
        holder.titleTextView.text = currentItem.title
        holder.createTimeTextView.text = "${currentItem.user_name} создал обсуждение: " + currentItem.create_time
        holder.changeTimeTextView.text = "Последнее изменение: " + currentItem.last_change

        Glide.with(context)
            .load(currentItem.user_image)
            .placeholder(R.drawable.image_plug)
            .into(holder.imageView)

        holder.ratingNumberTextView.text = currentItem.rating.toString()
        holder.viewNumberTextView.text = currentItem.view.toString()

        if (userId == communityList[position].id_user) {
            holder.deleteView.visibility = View.VISIBLE
        }

        holder.deleteView.setOnClickListener {
            onDeleteItemClickListener.onDeleteItemClick(position, communityList[position])
        }

        holder.ratingImageView.setImageResource(
            if (currentItem.rating < 0) {
                R.drawable.arrow_down_rating
            } else {
                R.drawable.arrow_up_rating
            }
        )

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return communityList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val createTimeTextView: TextView = itemView.findViewById(R.id.create_time)
        val changeTimeTextView: TextView = itemView.findViewById(R.id.change_time)
        val ratingNumberTextView: TextView = itemView.findViewById(R.id.rating_number)
        val viewNumberTextView: TextView = itemView.findViewById(R.id.view_number)
        var ratingImageView: ImageView = itemView.findViewById(R.id.arrow_rating)
        val imageView: ImageView = itemView.findViewById(R.id.icon_user)
        val deleteView: ImageView = itemView.findViewById(R.id.delete_icon)
    }
}