package com.example.vehiclecontacting.TabFragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.view_userinfo.*

class UserFragment: Fragment() {

    var parentContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        val status = InfoRepository.loginStatus.status
        val user = InfoRepository.user
        if (status) {
            if (user != null) {
                loadInfo()
            }
        }
        else {
            loginEvent()
        }
    }

    private fun loginEvent() {
        user_username.setOnClickListener {
            val loginIntent = Intent(parentContext, LoginActivity::class.java)
            startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
        }
    }

    private fun loadInfo() {
        user_username.text = InfoRepository.user!!.username?:""
        user_vipstatus.text =
            if (InfoRepository.user!!.vip > 0) "vip${InfoRepository.user!!.vip}" else "未开通VIP服务"
        user_moments.text = InfoRepository.user!!.momentCounts.toString()
        user_follow.text = InfoRepository.user!!.followCounts.toString()
        user_fans.text = InfoRepository.user!!.fansCounts.toString()
        if (InfoRepository.user!!.photo != null)
            user_avt.setAvt(InfoRepository.user!!.photo)
        user_username.isClickable = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_LOGIN -> {
                    val status = data?.getBooleanExtra(StatusRepository.loginStatus, false)
                    if (status == true) {
                        loadInfo()
                    }
                }
            }
        }
    }
}