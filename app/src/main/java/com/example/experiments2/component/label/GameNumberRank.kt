package com.example.experiments2.component.label

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R


class GameNormalLabel @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.component_label, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameButton)
        try {
            val text = ta.getString(R.styleable.GameButton_text)
            val textSize = ta.getDimension(R.styleable.GameButton_textSize, -0.1F)

            val textview = findViewById<TextView>(R.id.tv_name)

            textview.text = text
            if (textSize >= 0) textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        } finally {
            ta.recycle()
        }
    }
}