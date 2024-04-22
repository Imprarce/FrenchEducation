package com.imprarce.android.feature_home.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.imprarce.android.feature_home.R
import com.imprarce.android.feature_home.databinding.FragmentSettingsBinding
import com.imprarce.android.feature_home.ui.MainViewModel
import com.imprarce.android.frencheducation.utils.ImagePicker
import com.imprarce.android.frencheducation.utils.ThemeUtil.checkTheme
import com.imprarce.android.frencheducation.utils.ThemeUtil.toggleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var logoutListener: LogoutListener? = null
    private lateinit var imagePicker: ImagePicker
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutListener) {
            logoutListener = context
        } else {
            throw RuntimeException("$context must implement LogoutListener")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePicker = ImagePicker(this)
        imagePicker.onImageSelected = { imageUri ->
            viewModel.changePhoto(imageUri)
        }

        binding.toolbar.nameFragment.text = "Настройки"
        binding.nightTheme.text = if(checkTheme(requireContext())) "Вкл" else "Выкл"

        binding.logOutButton.setOnClickListener {
            viewModel.logOut()
            logoutListener?.onLogout()
        }


        binding.toolbar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.userFromRoom.observe(viewLifecycleOwner){ response ->
            response?.let {
                binding.name.text = response.userName
                binding.email.text = response.login
                Glide.with(requireContext())
                    .load(response.imageUrl)
                    .placeholder(R.drawable.image_plug)
                    .into(binding.photo)
            }
        }

        binding.layoutName.setOnClickListener {
            openChangeNameDialog()
        }

        binding.layoutPhoto.setOnClickListener {
            imagePicker.openGallery()
        }

        binding.layoutNightTheme.setOnClickListener {
            toggleTheme(requireActivity())

        }


        if (viewModel.userFromRoom.value == null) {
            viewModel.getUser()
        }

    }

    private fun openChangeNameDialog() {
        val dialogFragment = ChangeNameDialogFragment()
        dialogFragment.setChangeNameListener(object : ChangeNameDialogFragment.ChangeNameListener {
            override fun onChangeName(name: String) {
                viewModel.changeName(name)
            }
        })
        dialogFragment.show(childFragmentManager, "ChangeNameDialogFragment")
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}