package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_change_info_actvity.*

class ChangeInfoActivity : BaseActivity() {
    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_info_actvity)
        initData()
        initWidget()
    }

    private fun initData() {
        type = intent.getStringExtra("type")?:""
    }

    private fun initWidget() {
        when (type) {
            "username" -> {
                usernameEvent()
            }
            "description" -> {
                descriptionEvent()
            }
        }
        saveEvent()
    }

    private fun closeEvent() {
        changeInfo_close.setOnClickListener {
            finish()
        }
    }

    private fun usernameEvent() {
        changeInfo_username.visibility = View.VISIBLE
    }

    private fun descriptionEvent() {
        changeInfo_description.visibility = View.VISIBLE
    }

    private fun saveEvent() {
        changeInfo_save.setOnClickListener {
            when(type) {
                "username" -> {
                    val username = changeInfo_usernameEdit.text.toString()
                    val status = UserRepository.patchUserUsername(InfoRepository.user!!.id, username)
                    if (status == StatusRepository.SUCCESS) {
                        ToastView(this).show("修改昵称成功")
                        val intent = Intent()
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    else
                        ToastView(this).show("修改昵称失败")
                }
                "description" -> {
                    val description = changeInfo_descriptionEdit.text.toString()
                    val status = UserRepository.patchUserDescription(InfoRepository.user!!.id, description)
                    if (status == StatusRepository.SUCCESS) {
                        ToastView(this).show("修改个性签名成功")
                        val intent = Intent()
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    else
                        ToastView(this).show("修改个性签名失败")
                }
            }
        }
    }
}