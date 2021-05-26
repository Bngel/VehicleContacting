package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.*
import kotlinx.android.synthetic.main.activity_fans.*

class FansActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fans)
        initWidget()
    }

    private fun initWidget() {
        fansShowEvent()
        closeEvent()
    }

    private fun closeEvent() {
        fans_close.setOnClickListener {
            finish()
        }
    }

    private fun fansShowEvent() {
        val fansStatus = UserRepository.getFans(InfoRepository.user!!.id, 10, 1, "")
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