package com.example.experiments2.component.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton


class GameProfile(context: Context) : GameDialog(context) {

    companion object {
        fun newInstance(context: Context): GameProfile {
            return GameProfile(context)
        }
    }

    override fun initDialog(layoutId: Int?) {
        super.initDialog(R.layout.component_transfloat_profile)
    }

    fun show() {
        view.findViewById<ImageView>(R.id.iv_cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
