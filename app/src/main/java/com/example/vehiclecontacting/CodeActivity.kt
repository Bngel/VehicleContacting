package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_code.*
import java.util.*

class CodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)

        initWidget()
    }

    private fun initWidget() {
        val telArea = "telArea"
        val phone = "phone"
        val area = intent.getStringExtra(telArea)
        val tel = intent.getStringExtra(phone)
        code_tel.text = "$area $tel"
        code_inputCode.setOnCompleteListener {
            val codeType = "codeType"
            val type = intent.getIntExtra(codeType, 0)
            val password = WidgetSetting.getMD5(Date().time.toString())
            when (type) {
                UserRepository.TYPE_REGISTER -> {
                    if (tel != null) {
                        when (UserRepository.postRegister(it, password, tel)) {
                            StatusRepository.EXIST_WRONG -> {
                                ToastView(this).show("用户不存在")
                            }
                            StatusRepository.REPEAT_WRONG -> {
                                ToastView(this).show("验证码不存在或已过期")
                            }
                            StatusRepository.CODE_WRONG -> {
                                ToastView(this).show("验证码错误")
                            }
                            StatusRepository.UNKNOWN_WRONG -> {
                                ToastView(this).show("发生未知错误")
                            }
                            StatusRepository.SUCCESS -> {
                                TODO("SUCCESS")
                            }
                        }
                    }
                    else {
                        finish()
                    }
                }
                UserRepository.TYPE_LOGIN -> {
                    if (tel != null) {
                        when (UserRepository.postRegister(it, password, tel)) {
                            StatusRepository.EXIST_WRONG -> {
                                ToastView(this).show("账号不存在")
                            }
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
                                TODO("SUCCESS")
                            }
                        }
                    }
                    else {
                        finish()
                    }
                }
            }
        }
    }
}