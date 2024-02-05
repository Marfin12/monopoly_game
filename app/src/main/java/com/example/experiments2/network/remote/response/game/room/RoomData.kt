package com.example.experiments2.network.remote.response.game.room

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.experiments2.constant.Constant.MIN_PLAYER
import com.example.experiments2.network.remote.response.game.player.PlayerData
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RoomData(
    var roomId: String = "",
    var roomName: String = "",
    var roomMode: RoomEnum = RoomEnum.ERROR,
    var roomMaxPlayers: Int = MIN_PLAYER,
    var roomPlayers: HashMap<String, PlayerData> = HashMap()
) : Parcelable
