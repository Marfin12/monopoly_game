package com.example.experiments2.network.remote.response.game.player

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PlayerData(
    var isHost: Boolean = false,
    var playerImage: String = ""
) : Parcelable
