package com.imprarce.android.frencheducation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController = (childFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment).navController
        bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.communityFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.videoMenuFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.IELTSFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.dictionaryFragment -> bottomNavigationView.visibility = View.VISIBLE
                else -> bottomNavigationView.visibility = View.GONE
            }
        }

        setupOnBackPressedListener(navController)

    }

    private fun setupOnBackPressedListener(navController: NavController) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == R.id.homeFragment) {
                    requireActivity().finish()
                } else {
                    navController.popBackStack()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
