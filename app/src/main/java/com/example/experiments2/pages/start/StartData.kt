package com.example.experiments2.pages.start

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class StartData(
    val isSuccessful: Boolean? = null
) : Parcelable
