package com.example.experiments2.component.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import com.example.experiments2.R
import com.example.experiments2.component.button.GameGrayButton
import com.example.experiments2.component.button.GameNormalButton
import com.example.experiments2.pages.menu.MenuData


class GameSettings(context: Context) : GameDialog(context) {

    private lateinit var seekBarSound: SeekBar
    private lateinit var seekBarMusic: SeekBar
    private lateinit var positiveButton: GameNormalButton
    private lateinit var negativeButton: GameGrayButton

    companion object {
        fun newInstance(context: Context): GameSettings {
            return GameSettings(context)
        }
    }

    override fun initDialog(layoutId: Int?) {
        super.initDialog(R.layout.component_transfloat_settings)

        seekBarSound = view.findViewById(R.id.sb_sound)
        seekBarMusic = view.findViewById(R.id.sb_music)
        positiveButton = view.findViewById(R.id.positive_button)
        negativeButton = view.findViewById(R.id.negative_button)

        view.findViewById<ImageView>(R.id.iv_cancel).setOnClickListener { dismiss() }
    }

    fun show(
        sound: Int,
        music: Int,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveButtonClick: ((Int, Int) -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null,
    ) {
        generateMessage(
            sound, music,
            positiveButtonText, negativeButtonText,
            onPositiveButtonClick, onNegativeButtonClick
        )

        dialog.show()
    }

    private fun generateMessage(
        sound: Int,
        music: Int,
        positiveButtonText: String?,
        negativeButtonText: String?,
        onPositiveButtonClick: ((Int, Int) -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        seekBarSound.progress = sound
        seekBarMusic.progress = music

        if (positiveButtonText.isNullOrEmpty()) positiveButton.visibility = View.GONE
        else positiveButton.setOnClickListener {
            dialog.dismiss()
            onPositiveButtonClick?.invoke(seekBarSound.progress, seekBarMusic.progress)
        }

        if (negativeButtonText.isNullOrEmpty()) negativeButton.visibility = View.GONE
        else negativeButton.setOnClickListener {
            reset()
            onNegativeButtonClick?.invoke()
        }
    }

    private fun reset() {
        val defaultSetting = MenuData()

        seekBarSound.progress = defaultSetting.sound
        seekBarMusic.progress = defaultSetting.music
    }
}
