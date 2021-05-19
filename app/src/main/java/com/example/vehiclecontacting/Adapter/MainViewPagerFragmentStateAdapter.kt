package com.example.vehiclecontacting.Adapter

import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vehiclecontacting.StatusRepository.PAGE_COMMUNITY
import com.example.vehiclecontacting.TabFragment.CommunityFragment
import com.example.vehiclecontacting.TabFragment.HomeFragment
import com.example.vehiclecontacting.TabFragment.UserFragment
import com.example.vehiclecontacting.StatusRepository.PAGE_HOME
import com.example.vehiclecontacting.StatusRepository.PAGE_USER

class MainViewPagerFragmentStateAdapter(activity: AppCompatActivity, private val itemsCount: Int):
    FragmentStateAdapter(activity) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(PAGE_HOME, HomeFragment())
        fragments.put(PAGE_COMMUNITY, CommunityFragment())
        fragments.put(PAGE_USER, UserFragment())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount() = itemsCount
}