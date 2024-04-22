package com.imprarce.android.frencheducation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imprarce.android.feature_home.ui.settings.LogoutListener
import com.imprarce.android.frencheducation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LogoutListener {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        navController = (supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment).navController
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                com.imprarce.android.feature_home.R.id.homeFragment -> bottomNavigationView.visibility = View.VISIBLE
                com.imprarce.android.feature_community.R.id.communityFragment -> bottomNavigationView.visibility = View.VISIBLE
                com.imprarce.android.feature_video.R.id.videoMenuFragment -> bottomNavigationView.visibility = View.VISIBLE
                com.imprarce.android.feature_ielts.R.id.IELTSFragment -> bottomNavigationView.visibility = View.VISIBLE
                com.imprarce.android.feature_dictionary.R.id.dictionaryFragment -> bottomNavigationView.visibility = View.VISIBLE
                else -> bottomNavigationView.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == com.imprarce.android.feature_home.R.id.homeFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onLogout() {
        finish()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}