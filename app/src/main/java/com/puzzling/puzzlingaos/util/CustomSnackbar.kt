package com.puzzling.puzzlingaos.util

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.puzzling.puzzlingaos.databinding.SnackbarCustomBinding

object CustomSnackbar {
    fun makeSnackbar(view: View, message: String) {
        val inflater = LayoutInflater.from(view.context)
        val binding = SnackbarCustomBinding.inflate(inflater, null, false)

        binding.tvSnackbar.text = message

        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackbarLayout = snackbar.view as SnackbarLayout

        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 111.toPx())
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root)
        }

        snackbar.show()
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
