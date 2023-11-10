package com.example.experiments2.pages.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.experiments2.databinding.FragmentJoinGameBinding
import com.example.experiments2.pages.menu.room.RoomAdapter
import com.example.experiments2.pages.menu.room.RoomData


class JoinGameFragment : Fragment() {

    private lateinit var binding: FragmentJoinGameBinding
    private lateinit var joinRoomCard: ((String) -> Unit)
    private val roomCardAdapter = RoomAdapter(
        mutableListOf(
            RoomData("Room 1"),
            RoomData("Room 2"),
            RoomData("Room 3"),
            RoomData("Room 4")
        )
    )

    fun setJoinRoomCard(joinRoomCard: ((String) -> Unit)) {
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
                this.joinRoomCard(it.title)
            }
            println(roomCardAdapter.itemCount)
        }
    }
}