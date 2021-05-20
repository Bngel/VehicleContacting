package com.example.vehiclecontacting.TabFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vehiclecontacting.LoginActivity
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.InfoRepository
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
        user_username.text = "点击登录"
        user_vipstatus.text = "用户vip状态测试字段"
        user_moments.text = resources.getText(R.string.default_moments)
        user_follow.text = resources.getText(R.string.default_follow)
        user_fans.text = resources.getText(R.string.default_fans)
        loginEvent()
        testBtn.setOnClickListener {
            InfoRepository.initUser("1393953426531430402")

            Log.d("DataJson",InfoRepository.user.toString())
        }
    }

    private fun loginEvent() {
        user_username.setOnClickListener {
            val loginIntent = Intent(parentContext, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}