package com.example.vehiclecontacting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vehiclecontacting.Web.UserController.UserRepository
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    var isSelf = false

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
            finish()
        }
    }

    private fun followEvent() {
        detail_follow.visibility = View.VISIBLE
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
    }
}