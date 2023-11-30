package com.example.experiments2.component.label

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R


class GameNumberRank @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.component_label_rank, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.Rank)
        try {
            val playerRank = ta.getInt(R.styleable.Rank_playerNumber, 0)

            findViewById<TextView>(R.id.tv_rank).text = playerRank.toString()

            when(playerRank) {
                1 -> findViewById<ImageView>(R.id.holder_bg_button).setBackgroundColor(
                    resources.getColor(R.color.yellow_700, context.theme)
                )
                2 -> findViewById<ImageView>(R.id.holder_bg_button).setBackgroundColor(
                    resources.getColor(R.color.red_400, context.theme)
                )
                3 -> findViewById<ImageView>(R.id.holder_bg_button).setBackgroundColor(
                    resources.getColor(R.color.green_300, context.theme)
                )
                4 -> findViewById<ImageView>(R.id.holder_bg_button).setBackgroundColor(
                    resources.getColor(R.color.blue_900, context.theme)
                )
            }
        } finally {
            ta.recycle()
        }
    }
}