package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                        Log.d(StatusRepository.VehicleLog, "用户${tel}正在尝试注册")
                        when (UserRepository.postRegister(it, password, tel)) {
                            StatusRepository.EXIST_WRONG -> {
                                ToastView(this).show("验证码不存在或已过期")
                            }
                            StatusRepository.REPEAT_WRONG -> {
                                ToastView(this).show("该手机已被绑定")
                            }
                            StatusRepository.CODE_WRONG -> {
                                ToastView(this).show("验证码错误")
                            }
                            StatusRepository.UNKNOWN_WRONG -> {
                                ToastView(this).show("发生未知错误")
                            }
                            StatusRepository.SUCCESS -> {
                                ToastView(this).show("成功")
                            }
                        }
                    }
                    else {
                        finish()
                    }
                }
                UserRepository.TYPE_LOGIN -> {
                    if (tel != null) {
                        Log.d(StatusRepository.VehicleLog, "用户${tel}正在尝试登录")
                        when (UserRepository.postLoginByCode(it, phone)) {
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
                                ToastView(this).show("成功")
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