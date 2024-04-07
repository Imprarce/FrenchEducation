package com.imprarce.android.frencheducation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.ui.main.adapters.FilterAdapter


class DictionaryFragment : Fragment(R.layout.fragment_dictionary) {

    private val filterItems = listOf(R.string.level, R.string.sort_french, R.string.sort_rus, R.string.progress)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_filters)

        val adapter = FilterAdapter(filterItems)
        recyclerView.adapter = adapter
    }
}