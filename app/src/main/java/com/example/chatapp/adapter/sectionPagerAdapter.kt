package com.example.chatapp.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chatapp.fragments.ChatsFragment
import com.example.chatapp.fragments.UsersFragment


class SectionPagerAdapter(fragmentmanager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentmanager,lifecycle){
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChatsFragment()
            }
            1 -> {
                UsersFragment()
            }
            else -> {
                null!!
            }

        }
    }
}










