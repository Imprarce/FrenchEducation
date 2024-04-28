package com.imprarce.android.feature_video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imprarce.android.feature_video.adapters.FilterAdapter
import com.imprarce.android.feature_video.adapters.VideoAdapter
import com.imprarce.android.feature_video.databinding.FragmentVideoMenuBinding
import com.imprarce.android.feature_video.helpers.VideoClickListener
import com.imprarce.android.local.video.VideoItem

class VideoMenuFragment : Fragment(), VideoClickListener {
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding : FragmentVideoMenuBinding? = null
    private val binding get() = _binding!!

    private var userId = ""

    private val filterItems = listOf(R.string.rating, R.string.title, R.string.views)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       mainViewModel.deleteAllVideos()

        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

        userId = mainViewModel.getUserId()

        binding.addMessageButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString("user_id", userId)
            }
            findNavController().navigate(
                R.id.action_videoMenuFragment_to_addVideoFragment,
                bundle
            )
        }

        mainViewModel.videoList.observe(viewLifecycleOwner){ video ->
            setAdapter(video, userId)
        }
    }

    private fun setAdapter(videoList: List<VideoItem>, userId: String) {
        val adapter = VideoAdapter(videoList, userId, this)
        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onVideoClicked(videoUri: Uri, videoView: ImageView) {

        val bundle = Bundle().apply {
            putString("video_url", videoUri.toString())
        }
        findNavController().navigate(
            R.id.action_videoMenuFragment_to_videoPlayerFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}