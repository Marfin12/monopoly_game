package com.example.experiments2.component.transfloat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.experiments2.R


open class GameTransfloat @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs, null)
    }

    open fun init(attrs: AttributeSet?, layoutId: Int?) {
        View.inflate(context, R.layout.component_transfloat, this)

        if (layoutId != null) {
            val childView = LayoutInflater.from(context).inflate(layoutId, null) as ConstraintLayout
            val floatView = findViewById<ConstraintLayout>(R.id.float_view)

            floatView.addView(childView)

            childView.updateLayoutParams<LayoutParams> {
                topToTop = floatView.id
                bottomToBottom = floatView.id
                startToStart = floatView.id
                endToEnd = floatView.id
            }

            onTransparentDismiss(true)
        }
    }

    open fun onTransparentDismiss(isClickable: Boolean) {
        if (isClickable) {
            findViewById<View>(R.id.view_bg).setOnClickListener {
                this.visibility = View.GONE
            }
        }
    }
}
