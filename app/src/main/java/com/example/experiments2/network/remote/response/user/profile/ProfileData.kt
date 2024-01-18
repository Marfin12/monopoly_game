package com.example.experiments2.network.remote.response.user.profile

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProfileData(
    var username: String = "",
    var useremail: String = "",
    var userdate: String = "",
    var usergames: Int = 0,
    var userimage: String = "",
    var userrating: Int = 0,
    var usermatch: String = "",
    var userstate: ProfileEnum = ProfileEnum.ERROR,
    var usertoken: String = "",
) : Parcelable
