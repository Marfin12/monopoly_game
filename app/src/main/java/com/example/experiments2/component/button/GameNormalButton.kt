package com.example.experiments2.component.button

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R


open class GameNormalButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.component_button, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameButton)
        try {
            val txtButton = findViewById<TextView>(R.id.tv_normal_text)
            val txtSymbol = findViewById<TextView>(R.id.tv_symbol)
            val txtWithSymbol = findViewById<TextView>(R.id.tv_with_symbol)
            val ivSymbol = findViewById<ImageView>(R.id.iv_symbol)
            val ivBackground = findViewById<ImageView>(R.id.holder_bg_button)

            generateSymbol(ta, txtButton, txtSymbol, txtWithSymbol, ivSymbol)
            generateButton(ta, txtButton)
            generateButton(ta, txtWithSymbol)
            generateColor(ta, txtButton, txtSymbol, ivBackground)
        } finally {
            ta.recycle()
        }
    }

    private fun generateColor(
        ta: TypedArray,
        txtButton: TextView,
        txtWithSymbol: TextView,
        ivBackground: ImageView
    ) {
        txtButton.setTextColor(
            resources.getColor(
                ta.getResourceId(R.styleable.GameButton_textColor, R.color.black),
                context.theme
            )
        )
        txtWithSymbol.setTextColor(
            resources.getColor(
                ta.getResourceId(R.styleable.GameButton_textColor, R.color.black),
                context.theme
            )
        )
        ivBackground.setImageResource(ta.getResourceId(
            R.styleable.GameButton_buttonColor,
            R.drawable.white_rounded_bg_8_rad
        ))
    }

    private fun generateSymbol(
        ta: TypedArray,
        txtButton: TextView,
        txtSymbol: TextView,
        txtWithSymbol: TextView,
        ivSymbol: ImageView
    ) {
        val symbol = ta.getString(R.styleable.GameButton_symbol)
        val symbolLogo = ta.getResourceId(
            R.styleable.GameButton_symbol_logo,
            0
        )
        val symbolTop = ta.getInteger(R.styleable.GameButton_symbol_top, 0)
        val symbolBottom = ta.getInteger(R.styleable.GameButton_symbol_bottom, 0)
        val symbolEnd = ta.getInteger(R.styleable.GameButton_symbol_end, 0)
        val symbolStart = ta.getInteger(R.styleable.GameButton_symbol_start, 0)

        val text = ta.getString(R.styleable.GameButton_text)

        if (symbol.isNullOrBlank() && symbolLogo == 0) {
            txtButton.text = text
            txtButton.visibility = View.VISIBLE
            txtSymbol.visibility = View.GONE
            txtWithSymbol.visibility = View.GONE
        }
        else if(!symbol.isNullOrBlank()) {
            txtButton.visibility = View.INVISIBLE
            txtWithSymbol.visibility = View.VISIBLE
            txtSymbol.visibility = View.VISIBLE

            txtSymbol.text = symbol

            txtWithSymbol.text = text
            txtButton.text = text
            txtSymbol.y += (symbolTop - symbolBottom)
            txtSymbol.x += (symbolStart - symbolEnd)
        }
        else if(symbolLogo != 0) {
            txtButton.visibility = View.INVISIBLE
            txtWithSymbol.visibility = View.VISIBLE
            ivSymbol.visibility = View.VISIBLE

            txtWithSymbol.text = text
            txtButton.text = text

            ivSymbol.setImageResource(symbolLogo)
        }
    }

    private fun generateButton(ta: TypedArray, tvButton: TextView) {
        tvButton.setPadding(
            ta.getDimensionPixelSize(
                R.styleable.GameButton_button_textPaddingLeft,
                tvButton.paddingLeft
            ),
            ta.getDimensionPixelSize(
                R.styleable.GameButton_button_textPaddingTop,
                tvButton.paddingTop
            ),
            ta.getDimensionPixelSize(
                R.styleable.GameButton_button_textPaddingRight,
                tvButton.paddingRight
            ),
            ta.getDimensionPixelSize(
                R.styleable.GameButton_button_textPaddingBottom,
                tvButton.paddingBottom
            ),
        )
    }
}