package com.example.vehiclecontacting.Adapter

import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vehiclecontacting.CommunityFragment.FollowFragment
import com.example.vehiclecontacting.CommunityFragment.RecommendFragment
import com.example.vehiclecontacting.Repository.StatusRepository


class CommunityViewPagerFragmentStateAdapter(activity: AppCompatActivity, private val itemsCount: Int):
    FragmentStateAdapter(activity) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(StatusRepository.PAGE_FOLLOW, FollowFragment())
        fragments.put(StatusRepository.PAGE_RECOMMEND, RecommendFragment())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount() = itemsCount
}