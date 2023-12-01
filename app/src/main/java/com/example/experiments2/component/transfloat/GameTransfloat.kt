package com.example.experiments2.component.transfloat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.experiments2.R
import com.example.experiments2.util.Util.addViewByLayoutId


open class GameTransfloat @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs, null)
    }

    open fun init(attrs: AttributeSet?, layoutId: Int?) {
        View.inflate(context, R.layout.component_transfloat, this)

        if (layoutId != null) {
            findViewById<ConstraintLayout>(R.id.float_view).addViewByLayoutId(layoutId)

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
