package com.imprarce.android.frencheducation.ui.settings_profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.utils.ThemeUtil.checkTheme

class ChangeNameDialogFragment : DialogFragment() {
    private lateinit var buttonChange: Button
    private lateinit var editTextChange: EditText

    interface ChangeNameListener {
        fun onChangeName(name: String)
    }

    private var listener: ChangeNameListener? = null

    fun setChangeNameListener(listener: ChangeNameListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_change, null)

            buttonChange = view.findViewById(R.id.buttonChange)
            editTextChange = view.findViewById(R.id.editTextChange)

            builder.setView(view)
                .setTitle("Поменять имя")

            val dialog = builder.create()

            val backgroundColor = if (checkTheme(requireContext())) {
                R.color.dark_purple
            } else {
                R.color.light_gray_line_reg
            }
            dialog.window?.setBackgroundDrawableResource(backgroundColor)

            dialog.setCanceledOnTouchOutside(true)

            buttonChange.setOnClickListener {
                val newName = editTextChange.text.toString().trim()
                listener?.onChangeName(newName)
                dismiss()
            }

            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}