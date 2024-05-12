package com.imprarce.android.frencheducation.ui.settings_profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentSettingsBinding
import com.imprarce.android.frencheducation.ui.MainViewModel
import com.imprarce.android.frencheducation.ui.helpers.LogoutListener
import com.imprarce.android.frencheducation.utils.ImagePicker
import com.imprarce.android.frencheducation.utils.ThemeUtil.checkTheme
import com.imprarce.android.frencheducation.utils.ThemeUtil.toggleTheme
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var logoutListener: LogoutListener? = null
    private lateinit var imagePicker: ImagePicker
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val PERMISSION_REQUEST_CODE = 123

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutListener) {
            logoutListener = context
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

        requestPermissions()

        binding.nightTheme.text = if (checkTheme(requireContext())) "Вкл" else "Выкл"

        binding.logOutButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
            }
            logoutListener?.onLogout()
        }


        viewModel.userFromRoom.observe(viewLifecycleOwner) { response ->
            response?.let {
                binding.name.text = response.userName
                binding.email.text = response.email
                loadUserPhoto(response.imageUrl)
            }
        }

        viewModel.userPhotoUrl.observe(viewLifecycleOwner){ response ->
            loadUserPhoto(response)
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

    private fun checkPermissions(): Boolean {
        val readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        val writePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )
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

    private fun loadUserPhoto(imageUrl: String?) {
        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(R.drawable.image_plug)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.photo)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}