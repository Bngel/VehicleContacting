package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.*
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_fans.*
import kotlinx.android.synthetic.main.fragment_recommend.*

class FansActivity : BaseActivity() {
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fans)
        initWidget()
    }

    private fun initWidget() {
        fansShowEvent()
        closeEvent()
        refreshEvent()
    }

    private fun closeEvent() {
        fans_close.setOnClickListener {
            finish()
        }
    }

    private fun refreshEvent() {
        fans_refresh.setEnableRefresh(false)
        fans_refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                Handler().postDelayed(Runnable {
                    page ++
                    if (page <= UserRepository.fansPage){
                        DiscussRepository.getDiscuss(100, 1, page, 0)
                    }
                    else
                        ToastView(baseContext).show("已无更多粉丝")
                    fans_refresh.finishLoadmore()
                }, 2000)
            }
        })
    }

    private fun fansShowEvent() {
        val fansStatus = UserRepository.getFans(InfoRepository.user!!.id, 100, 1, "")
        if (fansStatus != StatusRepository.SUCCESS)
            ToastView(this).show("获取用户粉丝信息失败")
        fans_fans.removeAllViews()
        for (fans in UserRepository.fansList) {
            val view = UserCardView(this, fans.id, fans.photo, fans.username, fans.introduction?:"")
            val followBtn = view.findViewById<FollowView>(R.id.userCard_follow)
            val followStatus = UserRepository.postJudgeFavor(InfoRepository.user!!.id, fans.id)
            if (followStatus == StatusRepository.SUCCESS)
                followBtn.setStatus(UserRepository.FOLLOW_NOT)
            else
                followBtn.setStatus(UserRepository.followStatus)
            followBtn.cardFollow(fans.id)
            val fansAvt = view.findViewById<AvtView>(R.id.userCard_avt)
            fansAvt.setOnClickListener {
                val detailIntent = Intent(this, UserDetailActivity::class.java)
                detailIntent.putExtra("isSelf", false)
                detailIntent.putExtra("id", fans.id)
                startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
            }
            fans_fans.addView(view)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_DETAIL -> {
                    fansShowEvent()
                }
            }
        }
    }
}