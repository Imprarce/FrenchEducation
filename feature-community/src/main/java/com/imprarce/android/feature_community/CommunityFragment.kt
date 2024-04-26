package com.imprarce.android.feature_community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.imprarce.android.feature_community.adapters.CommunityAdapter
import com.imprarce.android.feature_community.adapters.FilterAdapter
import com.imprarce.android.feature_community.databinding.FragmentCommunityBinding
import com.imprarce.android.feature_community.helpers.OnCommunityItemClickListener
import com.imprarce.android.feature_community.helpers.OnDeleteItemClickListener
import com.imprarce.android.local.community.CommunityItem
import com.mikhaellopez.circularimageview.CircularImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment(), OnDeleteItemClickListener<CommunityItem>, OnCommunityItemClickListener {
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    private var userId = ""

    private val filterItems = listOf(R.string.rating, R.string.title, R.string.views)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = mainViewModel.getUserId()
        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

        binding.addMessageButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString("user_id", userId)
            }
            findNavController().navigate(
                R.id.action_communityFragment_to_addMessageFragment,
                bundle
            )
        }

        mainViewModel.communityList.observe(viewLifecycleOwner) { community ->
            setAdapter(community, userId)
        }
    }

    private fun setAdapter(communityList: List<CommunityItem>, userId: String) {
        val adapter = CommunityAdapter(requireContext(), communityList, userId, this, this)
        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDeleteItemClick(position: Int, item: CommunityItem) {
        mainViewModel.deleteCommunity(item)
    }

    override fun onItemClicked(item: CommunityItem) {
        val bundle = bundleOf("id_community" to item.id_community, "user_id" to userId)
        findNavController().navigate(R.id.action_communityFragment_to_detailMessageFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}