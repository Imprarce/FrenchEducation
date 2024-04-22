package com.imprarce.android.feature_home

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
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
            navigateWithAnimations(R.id.settingsFragment)
        }

        iconUserImageView.setOnClickListener {
            navigateWithAnimations(R.id.profileFragment)
        }

    }

    private fun navigateWithAnimations(destinationId: Int) {
        val navOptions = navOptions {
            anim {
                if(destinationId == R.id.profileFragment){
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                } else {
                    enter = R.anim.slide_in_left
                    exit = R.anim.slide_out_right
                    popEnter = R.anim.slide_in_right
                    popExit = R.anim.slide_out_left
                }
            }
        }

        findNavController().navigate(destinationId, null, navOptions)
    }
}
