package com.example.experiments2.pages.menu.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class TabAdapter(
    fragmentManager: FragmentManager,
    roomCard: ((String) -> Unit)
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var roomCard: ((String) -> Unit)

    init {
        this.roomCard = roomCard
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = JoinGameFragment()
                fragment.setJoinRoomCard(this.roomCard)
                return fragment
            }
            1 -> {
                val fragment = CreateGameFragment()
                fragment.setCreateRoomCard(this.roomCard)
                return fragment
            }
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Join Game"
            1 -> "Create Game"
            else -> null
        }
    }
}
