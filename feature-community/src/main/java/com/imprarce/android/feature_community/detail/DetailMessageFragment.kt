package com.imprarce.android.feature_community.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.imprarce.android.feature_community.MainViewModel
import com.imprarce.android.feature_community.R
import com.imprarce.android.feature_community.adapters.CommentAdapter
import com.imprarce.android.feature_community.adapters.CommunityAdapter
import com.imprarce.android.feature_community.databinding.FragmentDetailMessageBinding
import com.imprarce.android.local.comment.CommentItem
import com.imprarce.android.local.community.CommunityItem

class DetailMessageFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentDetailMessageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMessageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idUser = arguments?.getInt("user_id")
        val idCommunity = arguments?.getInt("id_community")

        if(idCommunity != null){
            mainViewModel.getCommunityItem(idCommunity)
            mainViewModel.getCommentsList(idCommunity)
        }

        mainViewModel.communityItem.observe(viewLifecycleOwner){ community ->
            binding.title.text = community.title
            binding.createTime.text = "${community.user_name} создал обсуждение: " + community.create_time
            binding.changeTime.text = "Последнее изменение: " + community.last_change
            binding.description.text = community.description
            binding.ratingNumber.text = community.rating.toString()

            Glide.with(requireContext())
                .load(community.user_image)
                .placeholder(R.drawable.image_plug)
                .into(binding.iconUser)
        }

        mainViewModel.commentsList.observe(viewLifecycleOwner){ comments ->
            setAdapter(comments)
        }

        binding.arrowUpRating.setOnClickListener {

        }

        binding.arrowDownRating.setOnClickListener {

        }

        binding.sendButton.setOnClickListener {
            if(binding.messageComment.text.isNotEmpty() && idUser != null && idCommunity != null){
                mainViewModel.insertComment(idUser, idCommunity, binding.messageComment.text.toString(), 0)
            }
        }
    }

    private fun setAdapter(commentsList: List<CommentItem>) {
        val adapter = CommentAdapter(commentsList, requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}