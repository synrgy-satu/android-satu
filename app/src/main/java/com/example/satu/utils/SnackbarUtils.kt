package com.example.satu.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.example.satu.R
import com.google.android.material.snackbar.Snackbar

object SnackbarUtils {
    fun showWithDismissAction(view: View, message: Any) {
        val messageText = when (message) {
            is Int -> view.context.getString(message)
            is String -> message
            else -> throw IllegalArgumentException("Unsupported message type")
        }
        Snackbar.make(view, messageText, Snackbar.LENGTH_LONG)
            .apply {
                setActionTextColor(ContextCompat.getColor(view.context, R.color.primary))
                setAction("Dismiss") { dismiss() }
                view.announceForAccessibility(messageText)
                view.contentDescription = messageText
                view.isFocusable = true
                view.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            }
            .show()
    }
}