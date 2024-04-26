package com.imprarce.android.frencheducation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.ActivityMainBinding
import com.imprarce.android.frencheducation.ui.settings_profile.LogoutListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LogoutListener {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = findViewById<ImageView>(R.id.settingsButton)
        val profile = findViewById<ImageView>(R.id.icon_user)
        val backArrow = findViewById<ImageButton>(R.id.back_arrow)

        viewModel.userFromRoom.observe(this) { response ->
            response?.let {
                Glide.with(applicationContext)
                    .load(response.imageUrl)
                    .placeholder(com.imprarce.android.feature_community.R.drawable.image_plug)
                    .into(profile)
            }
        }


        if (viewModel.userFromRoom.value == null) {
            viewModel.getUser()
        }

        settings.setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }

        profile.setOnClickListener {
            navController.navigate(R.id.profileFragment)
        }

        backArrow.setOnClickListener {
            navController.popBackStack()
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        navController = (supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment).navController
        bottomNavigationView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.imprarce.android.feature_home.R.id.homeFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    changeToolBar(true)
                }
                com.imprarce.android.feature_community.R.id.communityFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    changeToolBar(true)
                }
                com.imprarce.android.feature_video.R.id.videoMenuFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    changeToolBar(true)
                }
                com.imprarce.android.feature_ielts.R.id.IELTSFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    changeToolBar(true)
                }
                com.imprarce.android.feature_dictionary.R.id.dictionaryFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    changeToolBar(true)
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                    changeToolBar(false)
                }
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

    private fun changeToolBar(flag: Boolean) {
        if (!flag) {
            binding.toolbar.backArrow.visibility = View.VISIBLE
            binding.toolbar.nameFragment.visibility = View.VISIBLE

            binding.toolbar.nameFragment.text = navController.currentDestination?.navigatorName

            binding.toolbar.iconUser.visibility = View.GONE
            binding.toolbar.settingsButton.visibility = View.GONE
            binding.toolbar.searchView.visibility = View.GONE
        } else {
            binding.toolbar.backArrow.visibility = View.GONE
            binding.toolbar.nameFragment.visibility = View.GONE
            binding.toolbar.iconUser.visibility = View.VISIBLE
            binding.toolbar.settingsButton.visibility = View.VISIBLE
            binding.toolbar.searchView.visibility = View.VISIBLE
        }
    }

    override fun onLogout() {
        finish()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}