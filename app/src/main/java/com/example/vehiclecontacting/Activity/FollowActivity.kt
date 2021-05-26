package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.AvtView
import com.example.vehiclecontacting.Widget.FollowView
import com.example.vehiclecontacting.Widget.ToastView
import com.example.vehiclecontacting.Widget.UserCardView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_fans.*
import kotlinx.android.synthetic.main.activity_follow.*

class FollowActivity : BaseActivity() {

    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)
        initWidget()
    }

    private fun initWidget() {
        followShowEvent()
        closeEvent()
        refreshEvent()
    }

    private fun closeEvent() {
        follow_close.setOnClickListener {
            finish()
        }
    }

    private fun refreshEvent() {
        follow_refresh.setEnableRefresh(false)
        follow_refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                Handler().postDelayed(Runnable {
                    page ++
                    if (page <= UserRepository.fansPage){
                        DiscussRepository.getDiscuss(100, 1, page, 0)
                    }
                    else
                        ToastView(baseContext).show("已无更多粉丝")
                    follow_refresh.finishLoadmore()
                }, 2000)
            }
        })
    }

    private fun followShowEvent() {
        val followStatus = UserRepository.getFollow(InfoRepository.user!!.id, 100, 1, "")
        if (followStatus != StatusRepository.SUCCESS)
            ToastView(this).show("获取用户关注信息失败")
        follow_follows.removeAllViews()
        for (follow in UserRepository.followList) {
            val view = UserCardView(this, follow.id, follow.photo, follow.username, follow.introduction?:"")
            val followBtn = view.findViewById<FollowView>(R.id.userCard_follow)
            followBtn.setStatus(UserRepository.FOLLOW_ED)
            followBtn.cardFollow(follow.id)
            val followAvt = view.findViewById<AvtView>(R.id.userCard_avt)
            followAvt.setOnClickListener {
                val detailIntent = Intent(this, UserDetailActivity::class.java)
                detailIntent.putExtra("isSelf", false)
                detailIntent.putExtra("id", follow.id)
                startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
            }
            follow_follows.addView(view)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_DETAIL -> {
                    followShowEvent()
                }
            }
        }
    }
}