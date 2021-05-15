package com.example.vehiclecontacting

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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