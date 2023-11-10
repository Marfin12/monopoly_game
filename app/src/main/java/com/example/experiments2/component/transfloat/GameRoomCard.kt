package com.example.experiments2.component.transfloat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import com.example.experiments2.MyApplication.Companion.appResources
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton
import com.example.experiments2.util.Util.itemAdapterX


class GameRoomCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    GameTransfloat(context, attrs, defStyleAttr) {

    override fun init(attrs: AttributeSet?, layoutId: Int?) {
        super.init(attrs, R.layout.component_transfloat_roomcard)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.GameMessage)

        try {
            val title = ta.getString(R.styleable.GameMessage_title)

            generateRoom(title)
        } finally {
            ta.recycle()
        }
    }

    override fun onTransparentDismiss(isClickable: Boolean) {
        super.onTransparentDismiss(true)
    }

    fun show(title: String) {
        this.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tv_room_title).text = title
    }

    private fun generateRoom(title: String?) {
        findViewById<TextView>(R.id.tv_room_title).text = title
    }
}
