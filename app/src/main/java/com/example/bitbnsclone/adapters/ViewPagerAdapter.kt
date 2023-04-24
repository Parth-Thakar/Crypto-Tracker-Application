package com.example.bitbnsclone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bitbnsclone.fragments.LogInFragement
import com.example.bitbnsclone.fragments.SignUpFragment



class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
// View Page Adapter for the tab layout
    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            SignUpFragment()
        } else LogInFragement()
    }

    override fun getItemCount(): Int {
        return 2
    }
}