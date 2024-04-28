package com.imprarce.android.feature_video.helpers

import android.net.Uri
import android.widget.ImageView
import android.widget.VideoView

interface VideoClickListener {
    fun onVideoClicked(videoUri: Uri, videoView: ImageView)
}
