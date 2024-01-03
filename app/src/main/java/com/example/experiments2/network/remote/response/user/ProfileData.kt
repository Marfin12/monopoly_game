package com.example.experiments2.network.remote.response.user

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProfileData(
    val username: String = "",
    val token: String = ""
) : Parcelable
