package com.imprarce.android.frencheducation.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imprarce.android.frencheducation.R
import com.mikhaellopez.circularimageview.CircularImageView

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsButton = view.findViewById<ImageButton>(R.id.settingsButton)
        val iconUserImageView = view.findViewById<CircularImageView>(R.id.icon_user)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        iconUserImageView.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

    }
}
