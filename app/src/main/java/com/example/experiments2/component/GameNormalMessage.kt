package com.example.experiments2.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R


class GameNormalMessage @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    var onPositiveButtonClick: (() -> Unit)? = null
    var onNegativeButtonClick: (() -> Unit)? = null

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.component_game_message, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameMessage)
        try {
            val title = ta.getString(R.styleable.GameMessage_title)
            val message = ta.getString(R.styleable.GameMessage_message)
            val positiveButtonText = ta.getString(R.styleable.GameMessage_positive_button)
            val negativeButtonText = ta.getString(R.styleable.GameMessage_negative_button)

            generateMessage(title, message, positiveButtonText, negativeButtonText)
        } finally {
            ta.recycle()
        }
    }

    fun show(title: String, message: String, positiveButton: String? = null, negativeButton: String? = null) {
        this.visibility = View.VISIBLE
        generateMessage(title, message, positiveButton, negativeButton)
    }

    fun dismiss() {
        this.visibility = View.GONE
    }

    private fun generateMessage(
        title: String?,
        message: String?,
        positiveButtonText: String?,
        negativeButtonText: String?
    ) {
        val txtMessageTitle = findViewById<TextView>(R.id.tv_message_title)
        val txtMessageContent = findViewById<TextView>(R.id.tv_message_content)
        val positiveButton = findViewById<GameNormalButton>(R.id.positive_button)
        val negativeButton = findViewById<GameGrayButton>(R.id.negative_button)

        txtMessageTitle.text = title
        txtMessageContent.text = message

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
