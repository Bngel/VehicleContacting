package com.example.vehiclecontacting

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    enum class LoginWay {
        PASSWORD, AUTH
    }
    private var loginWay = LoginWay.AUTH
    private var checkStatus = false
    private var showPassword = false
    private var telValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 控件设置
        initWidget()
    }

    private fun initWidget() {
        checkEvent()
        switchLoginWayEvent()
        showPasswordEvent()
        telInputCheckEvent()
        clearTelEvent()
        loginEvent()
    }

    private fun loginEvent() {
        val codeType = "codeType"
        val telArea = "telArea"
        val phone = "phone"
        login_btn.setOnClickListener {
            if (loginWay == LoginWay.AUTH) {
                if (!telValid)
                    ToastView(this).show("请输入正确的手机号")
                else if (!checkStatus)
                    ToastView(this).show("请阅读并同意服务条款")
                else {
                    val tel = login_inputTel.text.toString()
                    val area = login_areaTel.text.toString()
                    val user = UserRepository.getUser(tel)
                    val loginFlag = user.id
                    val codeIntent = Intent(this, CodeActivity::class.java)
                    codeIntent.putExtra(telArea, area)
                    codeIntent.putExtra(phone, tel)
                    if (loginFlag == "") { // 获取User失败
                        UserRepository.postCode(tel, UserRepository.TYPE_REGISTER)
                        codeIntent.putExtra(codeType, UserRepository.TYPE_REGISTER)
                    }
                    else {
                        UserRepository.postCode(tel, UserRepository.TYPE_LOGIN)
                        codeIntent.putExtra(codeType, UserRepository.TYPE_LOGIN)
                    }
                    startActivityForResult(codeIntent, StatusRepository.ACTIVITY_CODE)
                }
            }
        }
    }

    private fun checkEvent() {
        login_agreecheck.setOnClickListener {
            checkStatus = if (!checkStatus) {
                login_agreecheck.setImageResource(R.drawable.bp_agree)
                if (telValid) {
                    login_btn.background = resources.getDrawable(R.drawable.bk_sendauth)
                    login_btn.isClickable = true
                }
                true
            } else {
                login_btn.background = resources.getDrawable(R.drawable.bk_sendauth_unclickable)
                login_btn.isClickable = false
                login_agreecheck.setImageResource(0)
                false
            }
        }
    }
    private fun switchLoginWayEvent() {
        login_way.setOnClickListener {
            loginWay = if (loginWay == LoginWay.AUTH) {
                login_password.visibility = View.VISIBLE
                login_tip.visibility = View.GONE
                login_way.text = resources.getText(R.string.login_auth)
                login_btn.text = resources.getText(R.string.login_login)
                LoginWay.PASSWORD
            } else {
                login_password.visibility = View.GONE
                login_tip.visibility = View.VISIBLE
                login_way.text = resources.getText(R.string.login_password)
                login_btn.text = resources.getText(R.string.login_sendAuth)
                LoginWay.AUTH
            }

        }
    }
    private fun showPasswordEvent() {
        login_showpswd.setOnClickListener {
            if (!showPassword) {
                login_inputPswd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                login_showpswd.setImageResource(R.drawable.gp_openeye)
                showPassword = true
            }
            else {
                login_inputPswd.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                login_showpswd.setImageResource(R.drawable.gp_closeeye)
                showPassword = false
            }
        }
    }
    private fun telInputCheckEvent() {
        login_inputTel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    telValid = s.length == 11
                    if (telValid && checkStatus) {
                        login_btn.background = resources.getDrawable(R.drawable.bk_sendauth)
                        login_btn.isClickable = true
                    }
                    else {
                        login_btn.background = resources.getDrawable(R.drawable.bk_sendauth_unclickable)
                        login_btn.isClickable = false
                    }
                }

            }

        })
    }
    private fun clearTelEvent() {
        login_clearTel.setOnClickListener {
            login_inputTel.setText("")
        }
    }
}