package com.example.experiments2.component.transfloat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton


class GameProfile @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    GameTransfloat(context, attrs, defStyleAttr) {

    var onPositiveButtonClick: (() -> Unit)? = null
    var onNegativeButtonClick: (() -> Unit)? = null
    var onDismiss: (() -> Unit)? = null

    override fun init(attrs: AttributeSet?, layoutId: Int?) {
        super.init(attrs, R.layout.component_transfloat_profile)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameMessage)
        try {
            findViewById<ImageView>(R.id.iv_cancel).setOnClickListener {
                dismiss()
            }
        } finally {
            ta.recycle()
        }
    }

    override fun onTransparentDismiss(isClickable: Boolean) {
        super.onTransparentDismiss(false)
        this.onDismiss?.invoke()
    }

    fun show(onDismiss: (() -> Unit)) {
        this.visibility = View.VISIBLE
        this.onDismiss = onDismiss
    }

    private fun dismiss() {
        this.visibility = View.GONE
    }
}
