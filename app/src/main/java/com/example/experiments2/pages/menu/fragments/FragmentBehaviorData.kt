package com.example.experiments2.pages.menu.fragments

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import com.example.experiments2.network.remote.response.game.room.RoomData
import com.example.experiments2.network.remote.response.settings.SettingData
import com.example.experiments2.network.remote.response.user.UserData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FragmentBehaviorData(
    var createRoomCard: ((RoomData, () -> Unit) -> Unit) = {_, _ ->},
    var onShowLoading: (() -> Unit) = {},
    var onHideLoading: (() -> Unit) = {},
    var onForceLogout: (() -> Unit) = {},
    var updateRoomCard: ((RoomData) -> Unit) = {_ ->}
) : Parcelable
