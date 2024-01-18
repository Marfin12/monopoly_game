package com.example.experiments2.network.remote.response.settings

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SettingData(
    var sound: Int = 50,
    var music: Int = 50
) : Parcelable
