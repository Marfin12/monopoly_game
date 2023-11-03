package com.example.experiments2.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R

class GameNormalButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.component_game_button, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameButton)
        try {
            val text = ta.getString(R.styleable.GameButton_text)
            val symbol = ta.getString(R.styleable.GameButton_symbol)
            val symbolTop = ta.getInteger(R.styleable.GameButton_symbol_top, 0)
            val symbolBottom = ta.getInteger(R.styleable.GameButton_symbol_bottom, 0)
            val symbolEnd = ta.getInteger(R.styleable.GameButton_symbol_end, 0)
            val symbolStart = ta.getInteger(R.styleable.GameButton_symbol_start, 0)

            val txtButton = findViewById<TextView>(R.id.tv_normal_text)
            val txtSymbol = findViewById<TextView>(R.id.tv_symbol)
            val txtWithSymbol = findViewById<TextView>(R.id.tv_with_symbol)
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