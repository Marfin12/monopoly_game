package com.example.experiments2.component.dialog

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton
import com.example.experiments2.component.enum.MessageStyle
import com.example.experiments2.constant.Constant
import com.example.experiments2.viewmodel.ViewModelEnum


class GameMessage(context: Context) : GameDialog(context) {

    private var isRTO = false

    companion object {
        fun newInstance(context: Context): GameMessage {
            return GameMessage(context)
        }
    }

    override fun initDialog(layoutId: Int?, styleId: Int?) {
        super.initDialog(R.layout.component_transfloat_message, null)
    }

    fun handleErrorMessage(
        vmEnum: ViewModelEnum,
        errorContent: String? = null,
        errorTitle: String? = null,
        positiveButtonClick: (() -> Unit)? = null
    ) {
        if (vmEnum == ViewModelEnum.ERROR) {
            val resources = context.resources

            when (errorContent) {
                Constant.ErrorType.REQUEST_TIME_OUT -> {
                    isRTO = true
                    showLoadingError(
                        resources.getString(R.string.error_network_connection_title),
                        resources.getString(R.string.error_network_connection_content)
                    )
                }
                Constant.ErrorType.OPERATION_LOCAL_FAILED -> {
                    isRTO = false
                    showError(
                        resources.getString(R.string.error_operation_local_failed_title),
                        resources.getString(R.string.error_operation_local_failed_content),
                        resources.getString(R.string.str_ok)
                    )
                }
                else -> {
                    isRTO = false
                    showError(
                        errorTitle ?: resources.getString(R.string.error_title),
                        errorContent ?: resources.getString(R.string.error_content),
                        resources.getString(R.string.str_ok),
                        onPositiveButtonClick = positiveButtonClick
                    )
                }
            }
        }
        else if (vmEnum == ViewModelEnum.COMPLETE) {
            if (isRTO) dialog.dismiss()
        }
    }

    fun show(
        title: String,
        message: String,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        generateMessage(
            title, message, positiveButtonText, negativeButtonText,
            onPositiveButtonClick, onNegativeButtonClick,
            MessageStyle.DEFAULT
        )

        dialog.show()
    }

    fun showError(
        title: String,
        message: String,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        generateMessage(
            title, message, positiveButtonText, negativeButtonText,
            onPositiveButtonClick, onNegativeButtonClick,
            MessageStyle.ERROR
        )

        dialog.show()
    }

    fun showLoading(title: String, message: String) {
        generateMessage(title, message, messageStyle = MessageStyle.LOADING)

        dialog.show()
    }

    private fun showLoadingError(title: String, message: String) {
        generateMessage(title, message, messageStyle = MessageStyle.LOADING_ERROR)

        dialog.setCancelable(false)
        dialog.show()
    }

    private fun generateMessage(
        title: String,
        message: String?,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null,
        messageStyle: MessageStyle?
    ) {
        val txtMessageTitle = view.findViewById<TextView>(R.id.tv_message_title)
        val txtMessageContent = view.findViewById<TextView>(R.id.tv_message_content)
        val positiveButton = view.findViewById<GameNormalButton>(R.id.positive_button)
        val negativeButton = view.findViewById<GameGrayButton>(R.id.negative_button)

        txtMessageTitle.text = title
        txtMessageContent.text = message

        if (positiveButtonText.isNullOrBlank()) positiveButton.visibility = View.GONE
        else {
            positiveButton.visibility = View.VISIBLE
            positiveButton.text = positiveButtonText
        }
        positiveButton.setOnClickListener {
            dialog.dismiss()
            onPositiveButtonClick?.invoke()
        }

        if (negativeButtonText.isNullOrBlank()) negativeButton.visibility = View.GONE
        else {
            negativeButton.visibility = View.VISIBLE
            negativeButton.text = negativeButtonText
        }
        negativeButton.setOnClickListener {
            dialog.dismiss()
            onNegativeButtonClick?.invoke()
        }

        when (messageStyle) {
            MessageStyle.ERROR -> {
                view.findViewById<ImageView>(R.id.iv_message_icon).visibility = View.VISIBLE
                view.findViewById<View>(R.id.view_title).background = ResourcesCompat.getDrawable(
                    context.resources, R.drawable.red_rounded_bg_32_rad, context.theme
                )
            }
            MessageStyle.LOADING -> {
                addProgressBar()
            }
            MessageStyle.LOADING_ERROR -> {
                view.findViewById<ImageView>(R.id.iv_message_icon).visibility = View.VISIBLE
                view.findViewById<View>(R.id.view_title).background = ResourcesCompat.getDrawable(
                    context.resources, R.drawable.red_rounded_bg_32_rad, context.theme
                )

                addProgressBar()
            }
            else -> {}
        }
    }

    private fun addProgressBar() {
        val progressBar = ProgressBar(context)

        view.findViewById<ConstraintLayout>(R.id.additional_view).addView(progressBar)
        dialog.setOnDismissListener {
            view.findViewById<ConstraintLayout>(R.id.additional_view).removeView(
                progressBar
            )
        }
    }
}
