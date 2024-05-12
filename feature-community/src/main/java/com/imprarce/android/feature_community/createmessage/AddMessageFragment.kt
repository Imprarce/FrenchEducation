package com.imprarce.android.feature_community.createmessage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imprarce.android.feature_community.MainViewModel
import com.imprarce.android.feature_community.databinding.FragmentAddMessageBinding
import com.imprarce.android.feature_community.utils.DateFormatUtil
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class AddMessageFragment : BottomSheetDialogFragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentAddMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMessageBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("user_id")

        setBottomSheet()

        binding.createButton.setOnClickListener {
            if (binding.title.text != "" && binding.description.text != "") {
                val currentDate = DateFormatUtil.getCurrentDate()
                if (userId != null) {
                    viewModel.addNewMessage(
                        userId,
                        binding.editTextTitle.text.toString(),
                        binding.editTextDescription.text.toString(),
                        LocalDateTime.parse(currentDate)
                    )
                }
                findNavController().popBackStack()
            }
        }
    }

    private fun setBottomSheet() {
        val bottomSheet: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!

        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}