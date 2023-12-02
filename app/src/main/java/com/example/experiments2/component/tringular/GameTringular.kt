package com.example.experiments2.component.tringular

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R
import com.example.experiments2.util.Util.addViewByLayoutId


open class GameTringular @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs, null)
    }

    open fun init(attrs: AttributeSet?, layoutId: Int?) {
        View.inflate(context, R.layout.component_tringular, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.Tringular)
        try {
            val ivBottomSide = findViewById<AppCompatImageView>(R.id.iv_bottom)
            val ivTopSide = findViewById<AppCompatImageView>(R.id.iv_top)
            val ivLeftSide = findViewById<AppCompatImageView>(R.id.iv_left)
            val ivRightSide = findViewById<AppCompatImageView>(R.id.iv_right)

            val isBottomSide = ta.getBoolean(R.styleable.Tringular_bottomSide, false)
            val isRightSide = ta.getBoolean(R.styleable.Tringular_rightSide, false)
            val isTopSide = ta.getBoolean(R.styleable.Tringular_topSide, false)
            val isLeftSide = ta.getBoolean(R.styleable.Tringular_leftSide, false)

            if (isBottomSide) setSide(layoutId, ivBottomSide)
            if (isRightSide) setSide(layoutId, ivRightSide)
            if (isTopSide) setSide(layoutId, ivTopSide)
            if (isLeftSide) setSide(layoutId, ivLeftSide)
        } finally {
            ta.recycle()
        }
    }

    private fun setSide(layoutId: Int?, view: View) {
        view.visibility = View.VISIBLE

        if (layoutId != null) {
            findViewById<ConstraintLayout>(R.id.float_view).addViewByLayoutId(layoutId, view)
        }
    }
}