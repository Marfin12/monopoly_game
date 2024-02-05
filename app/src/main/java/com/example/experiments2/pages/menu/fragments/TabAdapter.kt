package com.example.experiments2.pages.menu.fragments

import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.experiments2.network.remote.response.game.player.PlayerData
import com.example.experiments2.network.remote.response.game.room.RoomData


class TabAdapter(
    fragmentManager: FragmentManager,
    roomCard: ((RoomData, () -> Unit) -> Unit),
    onShowLoading: (() -> Unit),
    onHideLoading: (() -> Unit),
    onForceLogout: (() -> Unit),
    updateRoomCard: ((RoomData) -> Unit)
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var roomCard: ((RoomData, () -> Unit) -> Unit)
    private var onShowLoading: (() -> Unit)
    private var onHideLoading: (() -> Unit)
    private var onForceLogout: (() -> Unit)
    private var updateRoomCard: ((RoomData) -> Unit)

    init {
        this.roomCard = roomCard
        this.onShowLoading = onShowLoading
        this.onHideLoading = onHideLoading
        this.onForceLogout = onForceLogout
        this.updateRoomCard = updateRoomCard
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = JoinRoomFragment()
                fragment.setJoinRoomCard(this.roomCard)
                return fragment
            }
            1 -> {
                val fragment = CreateRoomFragment()
                fragment.setCreateRoomCard(
                    this.roomCard,
                    this.onShowLoading,
                    this.onHideLoading,
                    this.onForceLogout,
                    this.updateRoomCard
                )
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
