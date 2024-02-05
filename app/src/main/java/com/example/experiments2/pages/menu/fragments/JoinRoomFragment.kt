package com.example.experiments2.pages.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.experiments2.databinding.FragmentJoinGameBinding
import com.example.experiments2.network.remote.response.game.player.PlayerData
import com.example.experiments2.network.remote.response.game.room.RoomData
import com.example.experiments2.pages.menu.room.RoomAdapter
import com.example.experiments2.pages.menu.room.RoomData2


class JoinRoomFragment : Fragment() {

    private lateinit var binding: FragmentJoinGameBinding
    private lateinit var joinRoomCard: ((RoomData, () -> Unit) -> Unit)
    private val roomCardAdapter = RoomAdapter(
        mutableListOf(
            RoomData2("Room 1"),
            RoomData2("Room 2"),
            RoomData2("Room 3"),
            RoomData2("Room 4")
        )
    )

    fun setJoinRoomCard(joinRoomCard: ((RoomData, () -> Unit) -> Unit)) {
        this.joinRoomCard = joinRoomCard
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (activity != null) {

            binding.rvListCard.adapter = roomCardAdapter
            binding.rvListCard.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            roomCardAdapter.onItemClick = {
                this.joinRoomCard(RoomData()) {}
            }
//            println(roomCardAdapter.itemCount)
        }
    }
}