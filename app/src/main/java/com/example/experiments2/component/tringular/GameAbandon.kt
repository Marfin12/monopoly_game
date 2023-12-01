package com.example.experiments2.component.tringular

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.experiments2.R


class GameAbandon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    GameTringular(context, attrs, defStyleAttr) {

    override fun init(attrs: AttributeSet?, layoutId: Int?) {
        super.init(attrs, R.layout.component_tringular_abandon)

        val ivBottomSide = findViewById<AppCompatImageView>(R.id.iv_bottom)
        val ivTopSide = findViewById<AppCompatImageView>(R.id.iv_top)
        val ivLeftSide = findViewById<AppCompatImageView>(R.id.iv_left)
        val ivRightSide = findViewById<AppCompatImageView>(R.id.iv_right)

        setDarkTriangle(ivBottomSide)
        setDarkTriangle(ivTopSide)
        setDarkTriangle(ivLeftSide)
        setDarkTriangle(ivRightSide)
    }

    private fun setDarkTriangle(imageView: ImageView) {
        imageView.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
        imageView.alpha = 0.2F
    }
}