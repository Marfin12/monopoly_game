package com.example.experiments2.component.button

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.example.experiments2.R


class GameNextButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    GameNormalButton(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private fun init() {
        findViewById<ImageView>(R.id.holder_bg_button)
            .setImageResource(R.drawable.red_rounded_bg_8_rad)

        val txtButton = findViewById<TextView>(R.id.tv_normal_text)

        txtButton.setTextColor(resources.getColor(R.color.white, context.theme))
    }
}