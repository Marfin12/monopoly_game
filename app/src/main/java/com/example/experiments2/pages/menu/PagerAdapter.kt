package com.example.experiments2.pages.menu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.experiments2.pages.menu.fragments.CreateGameFragment
import com.example.experiments2.pages.menu.fragments.JoinGameFragment


class PagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> JoinGameFragment()
            1 -> CreateGameFragment()
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
