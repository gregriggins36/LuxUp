package com.frate.luxup.ui

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.frate.luxup.R

const val ARG_MESSAGE_STRING_RES_ID = "messageStringResIdArg"
const val ARG_MESSAGE_STRING = "messageStringArg"
const val ARG_POSITIVE_BUTTON_STRING_RES_ID = "positiveButtonStringResIdArg"

class SimpleDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.DialogTheme)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val width = resources.displayMetrics.widthPixels
        dialog.window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val args = arguments!!

        val positiveButtonId = args.getInt(ARG_POSITIVE_BUTTON_STRING_RES_ID, android.R.string.ok)
        val messageId = args.getInt(ARG_MESSAGE_STRING_RES_ID, 0)
        val message = args.getString(ARG_MESSAGE_STRING, "")

        val view = inflater.inflate(R.layout.fragment_simple_dialog, container, false)
        setupViews(view, positiveButtonId, messageId, message)

        return view
    }

    private fun setupViews(
            view: View,
            @StringRes positiveButtonId: Int,
            @StringRes messageId: Int,
            message: String) {
        if (message.isNotBlank()) {
            view.findViewById<TextView>(R.id.dialog_message).text = message
        } else {
            view.findViewById<TextView>(R.id.dialog_message).setText(messageId)
        }
        val buttonPositive = view.findViewById<TextView>(R.id.button_positive)
        buttonPositive.setText(positiveButtonId)
        buttonPositive.setOnClickListener { dismiss() }
    }
}
