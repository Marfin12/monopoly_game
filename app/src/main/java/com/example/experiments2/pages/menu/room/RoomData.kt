package com.example.experiments2.pages.menu.room

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RoomData(
    val title: String = ""
) : Parcelable