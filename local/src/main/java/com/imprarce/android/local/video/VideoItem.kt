package com.imprarce.android.local.video

import android.graphics.Bitmap

data class VideoItem (
     val videoId: Int,
     val rating: Int,
     val view: Int,
     val description: String,
     val title: String,
     val videoFile: String,
     val imageUrl: Bitmap?
)