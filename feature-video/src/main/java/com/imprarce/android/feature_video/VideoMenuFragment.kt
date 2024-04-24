package com.imprarce.android.feature_video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.imprarce.android.feature_video.adapters.FilterAdapter
import com.imprarce.android.feature_video.databinding.FragmentVideoMenuBinding
import com.mikhaellopez.circularimageview.CircularImageView

class VideoMenuFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding : FragmentVideoMenuBinding? = null
    private val binding get() = _binding!!

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

        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}