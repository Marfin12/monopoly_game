package com.example.experiments2.network.remote.response.game.room

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.experiments2.network.remote.response.user.match.MatchData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import kotlinx.parcelize.Parcelize

enum class RoomEnum {
    ONLINE, ONLINE_PLAY, BOTS, BOTS_PLAY, ERROR
}
