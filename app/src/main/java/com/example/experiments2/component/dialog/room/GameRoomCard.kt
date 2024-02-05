package com.example.experiments2.component.dialog.room

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R
import com.example.experiments2.component.button.GameNormalButton
import com.example.experiments2.component.dialog.GameDialog
import com.example.experiments2.component.dialog.profile.MatchAdapter
import com.example.experiments2.network.remote.response.game.player.PlayerData
import com.example.experiments2.network.remote.response.game.room.RoomData


class GameRoomCard(context: Context) : GameDialog(context) {

    companion object {
        fun newInstance(context: Context): GameRoomCard {
            return GameRoomCard(context)
        }
    }

    override fun initDialog(layoutId: Int?, styleId: Int?) {
        super.initDialog(R.layout.component_transfloat_roomcard, null)
    }

    fun show(roomData: RoomData) {
        generateMessage(roomData)

        dialog.show()
    }

    fun generateMessage(roomData: RoomData) {
        val tvRoomTitle = view.findViewById<AppCompatTextView>(R.id.tv_room_title)
        val tvMaxPlayer = view.findViewById<AppCompatTextView>(R.id.tv_max_player)
        val rvRoomPlayers = view.findViewById<RecyclerView>(R.id.rv_list_player)
        val positiveButton = view.findViewById<GameNormalButton>(R.id.btn_game_ok)

        tvRoomTitle.text = roomData.roomName
        tvMaxPlayer.text = context.getString(
            R.string.menu_create_game_room_max_player, roomData.roomMaxPlayers
        )

        rvRoomPlayers.adapter = PlayerRoomAdapter(roomData.roomPlayers, context)
        rvRoomPlayers.layoutManager = LinearLayoutManager(context)

        positiveButton.text = context.getString(R.string.menu_create_game_room_start)
        positiveButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}
