package com.example.experiments2.component.dialog

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


class GameRoomCard(context: Context) : GameDialog(context) {

    companion object {
        fun newInstance(context: Context): GameRoomCard {
            return GameRoomCard(context)
        }
    }

    override fun initDialog(layoutId: Int?, styleId: Int?) {
        super.initDialog(R.layout.component_transfloat_roomcard, null)
    }

    fun show(
        positiveButtonText: String? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
    ) {
        generateMessage(
            positiveButtonText,
            onPositiveButtonClick,
        )

        dialog.show()
    }

    private fun generateMessage(
        positiveButtonText: String?,
        onPositiveButtonClick: (() -> Unit)? = null,
    ) {
        val positiveButton = view.findViewById<GameNormalButton>(R.id.btn_game_ok)

        if (positiveButtonText.isNullOrEmpty()) positiveButton.visibility = View.GONE
        else positiveButton.setOnClickListener {
            dialog.dismiss()
            onPositiveButtonClick?.invoke()
        }
    }
}
