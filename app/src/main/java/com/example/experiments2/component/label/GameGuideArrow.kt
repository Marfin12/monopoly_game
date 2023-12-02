package com.example.experiments2.component.label

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.example.experiments2.MyApplication
import com.example.experiments2.R
import com.example.experiments2.component.enum.ArrowShape
import com.example.experiments2.util.Util.toDp


class GameGuideArrow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.component_label_arrow, this)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.Arrow)

        try {
            val parent = findViewById<ConstraintLayout>(R.id.parent_label_arrow)
            val textview = findViewById<TextView>(R.id.tv_rank)
            val imageView = findViewById<ImageView>(R.id.holder_bg_button)

            val arrowText = ta.getString(R.styleable.Arrow_arrowText)
            val arrowShape = ArrowShape.values().getOrNull(
                ta.getInt(R.styleable.Arrow_arrowShape, 0)
            )

            textview.text = arrowText

            when (arrowShape) {
                ArrowShape.STRAIGHT -> imageView.setImageResource(R.drawable.straight_guide_arrow)
                ArrowShape.CURVE -> generateCurveArrow(textview, imageView, parent)
                ArrowShape.CURVE_DOWN -> generateCurveDown(textview, imageView, parent)
                else -> println("nothing happen")
            }
        } finally {
            ta.recycle()
        }
    }

    private fun generateCurveArrow(tvRank: TextView, arrow: ImageView, parent: ConstraintLayout) {
        arrow.setImageResource(R.drawable.curve_guide_arrow)

        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)

        constraintSet.connect(
            arrow.id,
            ConstraintSet.TOP,
            tvRank.id,
            ConstraintSet.BOTTOM
        )

        constraintSet.connect(
            arrow.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )

        constraintSet.connect(
            tvRank.id,
            ConstraintSet.START,
            arrow.id,
            ConstraintSet.END
        )

        constraintSet.connect(
            tvRank.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )

        constraintSet.applyTo(parent)
        tvRank.x = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            -48F,
            context.resources.displayMetrics
        )
    }

    private fun generateCurveDown(tvRank: TextView, arrow: ImageView, parent: ConstraintLayout) {
        arrow.setImageResource(R.drawable.curve_guide_arrow)

        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)

        constraintSet.connect(
            arrow.id,
            ConstraintSet.TOP,
            tvRank.id,
            ConstraintSet.BOTTOM
        )

        constraintSet.connect(
            arrow.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )

        constraintSet.connect(
            tvRank.id,
            ConstraintSet.START,
            arrow.id,
            ConstraintSet.END
        )

        constraintSet.connect(
            tvRank.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )

        constraintSet.applyTo(parent)
//        tvRank.x = TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            0F,
//            context.resources.displayMetrics
//        )


        arrow.rotationX = 180F
        arrow.rotation = 45F
        arrow.setPadding(
            arrow.paddingLeft,
            arrow.paddingTop,
            arrow.paddingRight,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                24F,
                context.resources.displayMetrics
            ).toInt()
        )
        arrow.y = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            -24F,
            context.resources.displayMetrics
        )
    }
}