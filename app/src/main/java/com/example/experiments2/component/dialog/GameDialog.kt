package com.example.experiments2.component.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton
import com.example.experiments2.component.enum.MessageStyle
import com.example.experiments2.util.Util.addViewByLayoutId


open class GameDialog(val context: Context) {

    lateinit var dialog: AlertDialog
    lateinit var view: View

    var onDismissListener: (() -> Unit)? = null

    init {
        initDialog(null, null)
    }

    open fun dismiss() {
        dialog.dismiss()
    }

    open fun initDialog(layoutId: Int? = null, styleId: Int? = null) {
        if (layoutId != null) {
            val selectedStyleId = styleId ?: R.style.TransparentDialog

            val inflater = LayoutInflater.from(context)
            val builder = AlertDialog.Builder(context, selectedStyleId)

            view = inflater.inflate(layoutId, null)
            builder.setView(view)
            dialog = builder.create()
            dialog.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            dialog.setOnDismissListener {
                onDismissListener?.invoke()
            }
        }
    }
}
