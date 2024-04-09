package com.imprarce.android.frencheducation.ui.main.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.dictionary.DictionaryListItem
import com.imprarce.android.frencheducation.databinding.FragmentDictionaryBinding
import com.imprarce.android.frencheducation.ui.main.adapters.DictionaryAdapter
import com.imprarce.android.frencheducation.ui.main.adapters.FilterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryFragment : Fragment() {
    private val viewModel by viewModels<DictionaryViewModel>()
    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    private val filterItems = listOf(R.string.level, R.string.sort_french, R.string.sort_rus)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_filters)

        val adapter = FilterAdapter(filterItems)
        recyclerView.adapter = adapter

        viewModel.dictionaryList.observe(viewLifecycleOwner){ response ->
            setAdapter(response)
        }
    }

    private fun setAdapter(dictionaryList: List<DictionaryListItem>) {
        val adapter = DictionaryAdapter(dictionaryList)
        binding.recyclerViewDictionary.adapter = adapter
        binding.recyclerViewDictionary.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}