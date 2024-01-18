package com.example.experiments2.pages.menu

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import com.example.experiments2.network.remote.response.settings.SettingData
import com.example.experiments2.network.remote.response.user.UserData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MenuData(
    var userData: UserData = UserData(),
    var settingData: SettingData = SettingData(),
    var state: MenuEnum = MenuEnum.IDLE,
    var uri: Uri? = null
) : Parcelable
