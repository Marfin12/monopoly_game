package com.example.experiments2.pages.menu

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MenuData(
    var sound: Int = 50,
    var music: Int = 50,
    var state: MenuEnum = MenuEnum.IDLE
) : Parcelable
