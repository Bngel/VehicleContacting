package com.example.vehiclecontacting.TabFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.vehiclecontacting.*
import com.example.vehiclecontacting.Activity.CreateActivity
import com.example.vehiclecontacting.Activity.DiscussActivity
import com.example.vehiclecontacting.Activity.LoginActivity
import com.example.vehiclecontacting.Adapter.CommunityViewPagerFragmentStateAdapter
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.AnimRepository
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.CommunityCardView
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_recommend.*
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
            if (InfoRepository.loginStatus.status){
                AnimRepository.playAddArticleClickAnim(it as ImageView)
                val createIntent = Intent(parentContext, CreateActivity::class.java)
                startActivityForResult(createIntent, ActivityCollector.ACTIVITY_CREATE)
            }
            else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_DISCUSS -> {
                    val delete = data?.getBooleanExtra("update", false)
                    if (delete == true) {
                        DiscussRepository.discussList.clear()
                        recommend_cards.removeAllViews()
                        DiscussRepository.getDiscuss(10, 1, 1, 0)
                        if (parentContext != null) {
                            for (discuss in DiscussRepository.discussList) {
                                val view = CommunityCardView(parentContext!!, discuss.title, discuss.userPhoto, discuss.username,discuss.description,discuss.photo,
                                    discuss.likeCounts,discuss.commentCounts)
                                view.setOnClickListener {
                                    DiscussRepository.getComment(10, 1, 1, discuss.number)
                                    val discussIntent = Intent(parentContext, DiscussActivity::class.java)
                                    discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
                                    discussIntent.putExtra("comments", DiscussRepository.commentList)
                                    startActivityForResult(discussIntent, ActivityCollector.ACTIVITY_DISCUSS)
                                }
                                recommend_cards.addView(view)
                            }
                        }
                        data.putExtra("update", false)
                    }
                }
            }
        }
    }

}