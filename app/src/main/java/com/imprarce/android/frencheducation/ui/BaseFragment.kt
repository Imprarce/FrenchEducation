package com.imprarce.android.frencheducation.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.imprarce.android.frencheducation.R

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsButton = view.findViewById<ImageButton>(R.id.settingsButton)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
    }
}
