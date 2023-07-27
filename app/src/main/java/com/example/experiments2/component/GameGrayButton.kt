package com.example.experiments2.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R

class GameGrayButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.button_white_normal, this)
        findViewById<ImageView>(R.id.holder_bg_button).setImageResource(R.drawable.gray_rounded_bg_8_rad)

        val txtButton = findViewById<TextView>(R.id.tv_normal_text)
        val txtSymbol = findViewById<TextView>(R.id.tv_symbol)
        val txtWithSymbol = findViewById<TextView>(R.id.tv_with_symbol)

        txtButton.setTextColor(resources.getColor(R.color.white, context.theme))
        txtSymbol.setTextColor(resources.getColor(R.color.white, context.theme))
        txtWithSymbol.setTextColor(resources.getColor(R.color.white, context.theme))

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameButton)
        try {
            val text = ta.getString(R.styleable.GameButton_text)
            val symbol = ta.getString(R.styleable.GameButton_symbol)
            val symbolTop = ta.getInteger(R.styleable.GameButton_symbol_top, 0)
            val symbolBottom = ta.getInteger(R.styleable.GameButton_symbol_bottom, 0)
            val symbolEnd = ta.getInteger(R.styleable.GameButton_symbol_end, 0)
            val symbolStart = ta.getInteger(R.styleable.GameButton_symbol_start, 0)

            if (symbol.isNullOrEmpty()) {
                txtButton.text = text
                txtButton.visibility = View.VISIBLE
                txtSymbol.visibility = View.GONE
                txtWithSymbol.visibility = View.GONE
            }
            else {
                txtButton.visibility = View.INVISIBLE
                txtWithSymbol.visibility = View.VISIBLE
                txtSymbol.visibility = View.VISIBLE

                txtSymbol.text = symbol

                txtWithSymbol.text = text
                txtButton.text = text
                txtSymbol.y += (symbolTop - symbolBottom)
                txtSymbol.x += (symbolStart - symbolEnd)
            }
        } finally {
            ta.recycle()
        }
    }
}