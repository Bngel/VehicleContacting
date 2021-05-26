package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_code.*

class CodeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
        initWidget()
    }

    private fun closeEvent() {
        code_close.setOnClickListener {
            finish()
        }
    }

    private fun sendEvent() {
        val telArea = "telArea"
        val phone = "phone"
        val area = intent.getStringExtra(telArea)
        val tel = intent.getStringExtra(phone)
        code_tel.text = "$area $tel"
        code_inputCode.setOnCompleteListener {
            if (tel != null) {
                when (UserRepository.postLoginByCode(it, tel)) {
                    StatusRepository.CODE_EXIST_WRONG -> {
                        ToastView(this).show("验证码不存在或已失效")
                    }
                    StatusRepository.CODE_WRONG -> {
                        ToastView(this).show("验证码错误")
                    }
                    StatusRepository.FROZEN_WRONG -> {
                        ToastView(this).show("用户已被封号")
                    }
                    StatusRepository.UNKNOWN_WRONG -> {
                        ToastView(this).show("发生未知错误")
                    }
                    StatusRepository.SUCCESS -> {
                        ToastView(this).show("登录成功")
                        val intent = Intent()
                        intent.putExtra(StatusRepository.loginStatus, true)
                        setResult(RESULT_OK, intent)
                        InfoRepository.initLogin(this, tel)
                        finish()
                    }
                }
            }
            else {
                finish()
            }
        }
    }

    private fun initWidget() {
        sendEvent()
        closeEvent()
    }
}