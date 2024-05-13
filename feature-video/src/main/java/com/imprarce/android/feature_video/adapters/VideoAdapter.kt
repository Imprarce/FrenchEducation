package com.imprarce.android.feature_video.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imprarce.android.feature_video.R
import com.imprarce.android.feature_video.helpers.VideoClickListener
import com.imprarce.android.local.video.VideoItem

class VideoAdapter(
    private val videoList: List<VideoItem>,
    private val userId: String,
    private val videoClickListener: VideoClickListener?
) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = videoList[position]
        holder.titleTextView.text = currentItem.title
        holder.descriptionView.text = currentItem.description
        holder.ratingNumberTextView.text = currentItem.rating.toString()
        holder.viewNumberTextView.text = currentItem.view.toString()
        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(currentItem.videoFile.replace("http://", "https://"))
            .placeholder(R.drawable.image_plug_2)
            .into(holder.videoView)

        holder.videoView.setOnClickListener {
            videoClickListener?.onVideoClicked(Uri.parse(currentItem.videoFile.replace("http://", "https://")), holder.videoView)
        }

    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text)
        val descriptionView: TextView = itemView.findViewById(R.id.description_text)
        val ratingNumberTextView: TextView = itemView.findViewById(R.id.rating_number)
        val viewNumberTextView: TextView = itemView.findViewById(R.id.view_number)
        var ratingImageView: ImageView = itemView.findViewById(R.id.dislike)
        val videoView: ImageView = itemView.findViewById(R.id.video)
        val deleteView: ImageView = itemView.findViewById(R.id.delete_icon)
    }
}