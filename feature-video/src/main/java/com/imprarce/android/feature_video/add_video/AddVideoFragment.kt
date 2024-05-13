package com.imprarce.android.feature_video.add_video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imprarce.android.feature_video.MainViewModel
import com.imprarce.android.feature_video.databinding.FragmentAddVideoBinding
import com.imprarce.android.feature_video.utils.VideoPicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddVideoFragment : BottomSheetDialogFragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentAddVideoBinding? = null
    private val binding get() = _binding!!
    private var video_url: Uri? = null
    private val videoPicker: VideoPicker = VideoPicker(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddVideoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getString("user_id")

        setBottomSheet()

        binding.chooseVideo.setOnClickListener {
            videoPicker.openGallery()
        }

        videoPicker.onVideoSelected = { videoUri ->
            video_url = videoUri
            binding.chooseVideo.text = "Видео выбрано"
        }

        binding.createButton.setOnClickListener {
            if (binding.editTextTitle.text.toString() != "" && binding.editTextDescription.text.toString() != "") {
                if (video_url != null) {
                    viewModel.addNewVideo(
                        binding.editTextTitle.text.toString(),
                        binding.editTextDescription.text.toString(),
                        video_url!!
                    )
//                    lifecycleScope.launch {
//                        val videoIdDeferred = async {
//                            viewModel.getIdVideo(videoUri = Uri.parse(video_url))
//                        }
//                        val videoId = videoIdDeferred.await()
//                        viewModel.loadVideo(videoId, videoUri = Uri.parse(video_url))
//                    }
                }
                findNavController().popBackStack()
            }
        }
    }

    private fun setBottomSheet() {
        val bottomSheet: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!

        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}