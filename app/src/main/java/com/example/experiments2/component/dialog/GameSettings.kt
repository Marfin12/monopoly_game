package com.example.experiments2.component.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton


class GameSettings(context: Context) : GameDialog(context) {

    companion object {
        fun newInstance(context: Context): GameSettings {
            return GameSettings(context)
        }
    }

    override fun initDialog(layoutId: Int?) {
        super.initDialog(R.layout.component_transfloat_settings)
    }

    fun show(
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        generateMessage(
            positiveButtonText,
            negativeButtonText,
            onPositiveButtonClick,
            onNegativeButtonClick
        )

        dialog.show()
    }

    private fun generateMessage(
        positiveButtonText: String?,
        negativeButtonText: String?,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        val positiveButton = view.findViewById<GameNormalButton>(R.id.positive_button)
        val negativeButton = view.findViewById<GameGrayButton>(R.id.negative_button)

        if (positiveButtonText.isNullOrEmpty()) positiveButton.visibility = View.GONE
        else positiveButton.setOnClickListener {
            dialog.dismiss()
            onPositiveButtonClick?.invoke()
        }
        if (negativeButtonText.isNullOrEmpty()) negativeButton.visibility = View.GONE
        else negativeButton.setOnClickListener {
            dialog.dismiss()
            onNegativeButtonClick?.invoke()
        }
    }
}
