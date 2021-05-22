package com.example.vehiclecontacting.TabFragment

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.vehiclecontacting.Adapter.CommunityViewPagerFragmentStateAdapter
import com.example.vehiclecontacting.Adapter.MainViewPagerFragmentStateAdapter
import com.example.vehiclecontacting.AnimRepository
import com.example.vehiclecontacting.CreateActivity
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.StatusRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.view_communitytitle.*

class CommunityFragment: Fragment() {

    var parentContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        tabEvent()
        vpEvent()
        addEvent()
    }

    private fun tabEvent() {
        community_recommend.setOnClickListener {
            if (StatusRepository.communityTabStatus != StatusRepository.CommunityTab.RECOMMEND) {
                AnimRepository.playTextEnlargedClickAnim(it as TextView, -1)
                AnimRepository.playTextReducedClickAnim(community_follow, 1)
                StatusRepository.communityTabStatus = StatusRepository.CommunityTab.RECOMMEND
                community_viewpager.currentItem = StatusRepository.PAGE_RECOMMEND
            }
        }
        community_follow.setOnClickListener {
            if (StatusRepository.communityTabStatus != StatusRepository.CommunityTab.FOLLOW) {
                AnimRepository.playTextEnlargedClickAnim(it as TextView, 1)
                AnimRepository.playTextReducedClickAnim(community_recommend, -1)
                StatusRepository.communityTabStatus = StatusRepository.CommunityTab.FOLLOW
                community_viewpager.currentItem = StatusRepository.PAGE_FOLLOW
            }
        }
    }

    private fun vpEvent() {
        community_viewpager.adapter = CommunityViewPagerFragmentStateAdapter(activity as AppCompatActivity, 2)
        community_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    StatusRepository.PAGE_FOLLOW -> {
                        AnimRepository.playTextEnlargedClickAnim(community_follow, 1)
                        AnimRepository.playTextReducedClickAnim(community_recommend, -1)
                        StatusRepository.communityTabStatus =
                            StatusRepository.CommunityTab.FOLLOW
                        community_viewpager.currentItem = StatusRepository.PAGE_FOLLOW
                    }
                    StatusRepository.PAGE_RECOMMEND -> {
                        AnimRepository.playTextEnlargedClickAnim(community_recommend, -1)
                        AnimRepository.playTextReducedClickAnim(community_follow, 1)
                        StatusRepository.communityTabStatus =
                            StatusRepository.CommunityTab.RECOMMEND
                        community_viewpager.currentItem = StatusRepository.PAGE_RECOMMEND
                    }
                }
            }
        })
        (community_viewpager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        // community_viewpager.isUserInputEnabled = false
        // 设置无法滑动
    }

    private fun addEvent() {
        community_add.setOnClickListener {
            AnimRepository.playAddArticleClickAnim(it as ImageView)
            val createIntent = Intent(parentContext, CreateActivity::class.java)
            startActivity(createIntent)
        }
    }
}