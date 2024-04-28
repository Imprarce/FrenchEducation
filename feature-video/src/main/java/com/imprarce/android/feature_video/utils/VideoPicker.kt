package com.imprarce.android.feature_video.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class VideoPicker(private val fragment: Fragment) {

    private val getContent = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val videoUri: Uri? = data?.data
            Log.d("VideoPicker", "Video URI: $videoUri")
            videoUri?.let {
                onVideoSelected(it)
            }
        }
    }

    var onVideoSelected: (Uri) -> Unit = {}

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }
}