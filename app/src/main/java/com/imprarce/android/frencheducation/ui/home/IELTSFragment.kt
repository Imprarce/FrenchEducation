package com.imprarce.android.frencheducation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.ui.home.adapters.FilterAdapter


class IELTSFragment : Fragment(R.layout.fragment_i_e_l_t_s) {

    private val filterItems = listOf(R.string.title, R.string.level, R.string.progress)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_filters)

        val adapter = FilterAdapter(filterItems)
        recyclerView.adapter = adapter
    }
}