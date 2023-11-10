package com.example.experiments2.component.transfloat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton


class GameSettings @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    GameTransfloat(context, attrs, defStyleAttr) {

    var onPositiveButtonClick: (() -> Unit)? = null
    var onNegativeButtonClick: (() -> Unit)? = null

    override fun init(attrs: AttributeSet?, layoutId: Int?) {
        super.init(attrs, R.layout.component_transfloat_settings)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameMessage)
        try {
            val positiveButtonText = ta.getString(R.styleable.GameMessage_positive_button)
            val negativeButtonText = ta.getString(R.styleable.GameMessage_negative_button)

            generateMessage(positiveButtonText, negativeButtonText)

            findViewById<ImageView>(R.id.iv_cancel).setOnClickListener {
                dismiss()
            }
        } finally {
            ta.recycle()
        }
    }

    override fun onTransparentDismiss(isClickable: Boolean) {
        super.onTransparentDismiss(false)
    }

    fun show(positiveButton: String? = null, negativeButton: String? = null) {
        this.visibility = View.VISIBLE
        generateMessage(positiveButton, negativeButton)
    }

    private fun dismiss() {
        this.visibility = View.GONE
    }

    private fun generateMessage(
        positiveButtonText: String?,
        negativeButtonText: String?
    ) {
        val positiveButton = findViewById<GameNormalButton>(R.id.positive_button)
        val negativeButton = findViewById<GameGrayButton>(R.id.negative_button)

        if (positiveButtonText.isNullOrEmpty()) positiveButton.visibility = View.GONE
        else positiveButton.setOnClickListener {
            dismiss()
            onPositiveButtonClick?.invoke()
        }
        if (negativeButtonText.isNullOrEmpty()) negativeButton.visibility = View.GONE
        else negativeButton.setOnClickListener {
            dismiss()
            onNegativeButtonClick?.invoke()
        }
    }
}
