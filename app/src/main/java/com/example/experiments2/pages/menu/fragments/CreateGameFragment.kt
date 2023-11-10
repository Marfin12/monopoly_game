package com.example.experiments2.pages.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.experiments2.databinding.FragmentCreateGameBinding


class CreateGameFragment : Fragment() {

    private lateinit var binding: FragmentCreateGameBinding
    private lateinit var createRoomCard: ((String) -> Unit)

    fun setCreateRoomCard(createRoomCard: ((String) -> Unit)) {
        this.createRoomCard = createRoomCard
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (activity != null) {
            binding.btnStart.setOnClickListener {
                this.createRoomCard("room master")
            }
        }
    }
}