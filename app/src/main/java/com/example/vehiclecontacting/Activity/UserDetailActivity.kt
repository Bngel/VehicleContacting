package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.User
import com.example.vehiclecontacting.Web.UserController.UserRepository
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : BaseActivity() {

    var isSelf = false
    lateinit var userId: String
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        initData()
        initWidget()
    }

    private fun initWidget() {
        if (isSelf)
            editEvent()
        else
            followEvent()
        closeEvent()
    }

    private fun closeEvent(){
        detail_close.setOnClickListener {
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun followEvent() {
        detail_follow.visibility = View.VISIBLE
        val status = UserRepository.postJudgeFavor(InfoRepository.user!!.id, userId)
        if (status == StatusRepository.SUCCESS) {
            detail_follow.setStatus(UserRepository.followStatus)
            detail_follow.cardFollow(userId)
            detail_follow.isClickable = true
        }
        else {
            detail_follow.setStatus(UserRepository.FOLLOW_NOT)
            detail_follow.isClickable = false
        }
    }

    private fun editEvent() {
        detail_edit.visibility = View.VISIBLE
        detail_edit.setOnClickListener {
            val editIntent = Intent(this, UpdateInfoActivity::class.java)
            startActivityForResult(editIntent, ActivityCollector.ACTIVITY_EDIT)
        }
    }

    private fun initData() {
        isSelf = intent.getBooleanExtra("isSelf", false)
        userId = intent.getStringExtra("id")?:""
        user = UserRepository.getUserById(userId)
        if (user.id != "") {
            detail_avt.setAvt(user.photo)
            detail_momentsCount.text = user.momentCounts.toString()
            detail_followCount.text = user.followCounts.toString()
            detail_fansCount.text = user.fansCounts.toString()
            detail_username.text = user.username
            detail_vip.text = if (user.vip > 0) "vip${user.vip}" else "未开通VIP服务"
        }
    }
}