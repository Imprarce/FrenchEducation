package com.imprarce.android.feature_ielts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.imprarce.android.feature_ielts.adapters.FilterAdapter
import com.imprarce.android.feature_ielts.databinding.FragmentIELTSBinding
import com.mikhaellopez.circularimageview.CircularImageView


class IELTSFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding : FragmentIELTSBinding? = null
    private val binding get() = _binding!!

    private val filterItems = listOf(R.string.title, R.string.level, R.string.progress)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIELTSBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

        mainViewModel.userFromRoom.observe(viewLifecycleOwner){ url ->
            if(url != null) {
                val iconUser = view.findViewById<CircularImageView>(R.id.icon_user)
                Glide.with(requireContext())
                    .load(url.imageUrl)
                    .placeholder(R.drawable.image_plug)
                    .into(iconUser)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}