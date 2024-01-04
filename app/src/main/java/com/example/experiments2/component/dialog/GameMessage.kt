package com.example.experiments2.component.dialog

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton
import com.example.experiments2.component.enum.MessageStyle
import com.example.experiments2.constant.Constant
import com.example.experiments2.viewmodel.ViewModelEnum


class GameMessage(private val context: Context) : GameDialog(context) {

    companion object {
        fun newInstance(context: Context): GameMessage {
            return GameMessage(context)
        }
    }

    override fun initDialog(layoutId: Int?) {
        super.initDialog(R.layout.component_transfloat_message)
    }

    fun handleErrorMessage(
        vmEnum: ViewModelEnum,
        error: String?,
        positiveButtonClick: (() -> Unit)? = null
    ) {
        if (vmEnum == ViewModelEnum.ERROR) {
            val resources = context.resources

            when (error) {
                Constant.ErrorType.REQUEST_TIME_OUT -> {
                    showLoadingError(
                        resources.getString(R.string.error_network_connection_title),
                        resources.getString(R.string.error_network_connection_content)
                    )
                }
                Constant.ErrorType.OPERATION_LOCAL_FAILED -> {
                    showError(
                        resources.getString(R.string.error_operation_local_failed_title),
                        resources.getString(R.string.error_operation_local_failed_content),
                        resources.getString(R.string.str_ok)
                    )
                }
                else -> {
                    showError(
                        resources.getString(R.string.error_title),
                        error ?: resources.getString(R.string.error_content),
                        resources.getString(R.string.str_ok),
                        onPositiveButtonClick = positiveButtonClick
                    )
                }
            }
        }
        else if (vmEnum == ViewModelEnum.COMPLETE) {
            dialog.dismiss()
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
                view.findViewById<ConstraintLayout>(R.id.additional_view).addView(
                    ProgressBar(context)
                )
            }
            MessageStyle.LOADING_ERROR -> {
                view.findViewById<ImageView>(R.id.iv_message_icon).visibility = View.VISIBLE
                view.findViewById<View>(R.id.view_title).background = ResourcesCompat.getDrawable(
                    context.resources, R.drawable.red_rounded_bg_32_rad, context.theme
                )
                view.findViewById<ConstraintLayout>(R.id.additional_view).addView(
                    ProgressBar(context)
                )
            }
            else -> {}
        }
    }
}
