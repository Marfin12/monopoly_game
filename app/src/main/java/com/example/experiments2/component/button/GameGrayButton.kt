package com.example.experiments2.component.button

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.example.experiments2.R


class GameGrayButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    GameNormalButton(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private fun init() {
        findViewById<ImageView>(R.id.holder_bg_button)
            .setImageResource(R.drawable.gray_rounded_bg_8_rad)

        val txtButton = findViewById<TextView>(R.id.tv_normal_text)
        val txtSymbol = findViewById<TextView>(R.id.tv_symbol)
        val txtWithSymbol = findViewById<TextView>(R.id.tv_with_symbol)

        txtButton.setTextColor(resources.getColor(R.color.white, context.theme))
        txtSymbol.setTextColor(resources.getColor(R.color.white, context.theme))
        txtWithSymbol.setTextColor(resources.getColor(R.color.white, context.theme))
    }
}